package com.zaoyi.service.common.util;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 储存文件
 */
public class UploadFile {
	private static Logger logger=LoggerFactory.getLogger(UploadFile.class);
	
	public static String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
				String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
				String rondName=UUID.randomUUID().toString();
                String realFilePath=Configs.get().getString("system.upload_dir_name")+"/"+rondName+suffix;
                File saveDir = new File(realFilePath);
                if (!saveDir.getParentFile().exists()){
                	saveDir.getParentFile().mkdirs();
                }
                file.transferTo(saveDir);
                return rondName+suffix;
            } catch (Exception e) {
                logger.error("",e);
            }
        }
        return "";
    }
}
