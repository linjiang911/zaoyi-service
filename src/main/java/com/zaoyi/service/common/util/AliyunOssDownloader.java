package com.zaoyi.service.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;

public class AliyunOssDownloader {
	private static Logger logger = LoggerFactory.getLogger(AliyunOssDownloader.class);

	public static void downloadAndShutdown(OSSClient client, String bucketName) {
		try {
			System.out.println(JSONObject.toJSONString(client.listObjects(bucketName)));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	public static void main(String[] args) {
		String accessKeyId = Configs.get().getString("oss.access_key_id");
		String accessKeySecret = Configs.get().getString("oss.access_key_secret");
		String endpoint = Configs.get().getString("oss.apk.endpoint");
		String bucketName = Configs.get().getString("oss.apk.bucket_name");
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		downloadAndShutdown(ossClient, bucketName);
	}
}
