package com.zaoyi.service.common.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.zaoyi.service.common.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	@Override
	public String formatTimestamp(Long timestamp, String pattern) {
		return new SimpleDateFormat(pattern).format(new Date(timestamp));
	}
}
