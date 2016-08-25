package com.zaoyi.service.common.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

public class HttpKit {
	private static final String CHARSET = null;
	private static final String POST = null;

	/**
     * Send POST request
     */
    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
            conn.connect();
             
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes(CHARSET));
            out.flush();
            out.close();
             
            return readResponseString(conn);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

	private static HttpURLConnection getHttpConnection(Object buildUrlWithQueryString, String post2,
			Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object buildUrlWithQueryString(String url, Map<String, String> queryParas) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String readResponseString(HttpURLConnection conn) {
		// TODO Auto-generated method stub
		return null;
	}
}
