package com.zaoyi.service.common.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;

public class AliyunOssUploader {
	private static Logger logger = LoggerFactory.getLogger(AliyunOssUploader.class);

	public static void uploadAndShutdown(OSSClient client, String bucketName, String key, File file) {
		try {
			logger.info("Begin to upload to OSS from a file");
			client.putObject(new PutObjectRequest(bucketName, key, file));
			logger.info("Succeed to complete into an object named {}", key);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}
}
