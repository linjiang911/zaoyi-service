package com.zaoyi.service.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

public class AliyunOssMultipartUploader {
	private static Logger logger = LoggerFactory.getLogger(AliyunOssMultipartUploader.class);
	private static final long part_size = 5 * 1024 * 1024L; // 5MB
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

	public static synchronized void uploadAndShutdown(OSSClient client, String bucketName, String key, File file) {
		try {
			String uploadId = claimUploadId(client, bucketName, key);
			logger.info("Claiming a new upload id {}", uploadId);
			long fileLength = file.length();
			int partCount = (int) (fileLength / part_size);
			if (fileLength % part_size != 0) {
				partCount++;
			}
			if (partCount > 10000) {
				throw new RuntimeException("Total parts count should not exceed 10000");
			} else {
				logger.info("Total parts count {}", partCount);
			}
			logger.info("Begin to upload multiparts to OSS from a file");
			for (int i = 0; i < partCount; i++) {
				long startPos = i * part_size;
				long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : part_size;
				executorService.execute(
						new PartUploader(file, startPos, curPartSize, i + 1, uploadId, client, bucketName, key));
			}
			executorService.shutdown();
			while (!executorService.isTerminated()) {
				try {
					executorService.awaitTermination(5, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (partETags.size() != partCount) {
				throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
			} else {
				logger.info("Succeed to complete multiparts into an object named {}", key);
			}
			completeMultipartUpload(uploadId, client, bucketName, key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	private static class PartUploader implements Runnable {
		private File localFile;
		private long startPos;
		private long partSize;
		private int partNumber;
		private String uploadId;
		private OSSClient client;
		private String bucketName;
		private String key;

		public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId,
				OSSClient client, String bucketName, String key) {
			this.localFile = localFile;
			this.startPos = startPos;
			this.partSize = partSize;
			this.partNumber = partNumber;
			this.uploadId = uploadId;
			this.client = client;
			this.bucketName = bucketName;
			this.key = key;
		}

		@Override
		public void run() {
			InputStream instream = null;
			try {
				instream = new FileInputStream(this.localFile);
				instream.skip(this.startPos);
				UploadPartRequest uploadPartRequest = new UploadPartRequest();
				uploadPartRequest.setBucketName(bucketName);
				uploadPartRequest.setKey(key);
				uploadPartRequest.setUploadId(this.uploadId);
				uploadPartRequest.setInputStream(instream);
				uploadPartRequest.setPartSize(this.partSize);
				uploadPartRequest.setPartNumber(this.partNumber);
				UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
				logger.info("Part#{} done", this.partNumber);
				partETags.add(uploadPartResult.getPartETag());
			} catch (Exception e) {
				logger.error("", e);
			} finally {
				if (instream != null) {
					try {
						instream.close();
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
		}

	}

	private static String claimUploadId(OSSClient client, String bucketName, String key) {
		InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
		InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
		return result.getUploadId();
	}

	private static void completeMultipartUpload(String uploadId, OSSClient client, String bucketName, String key) {
		// Make part numbers in ascending order
		Collections.sort(partETags, new Comparator<PartETag>() {
			@Override
			public int compare(PartETag p1, PartETag p2) {
				return p1.getPartNumber() - p2.getPartNumber();
			}
		});
		logger.info("Completing to upload multiparts");
		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName,
				key, uploadId, partETags);
		client.completeMultipartUpload(completeMultipartUploadRequest);
	}
}
