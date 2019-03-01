package com.example.common.utils;

import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class HttpUtils {
    /**
     *
     /**
     * 获取response值
     * @param response
     * @return
     */
    public static String getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(wrapperResponse, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return "";
    }
}
