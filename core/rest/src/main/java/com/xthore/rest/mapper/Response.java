package com.xthore.rest.mapper;
 
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
 
public class Response {
 
    public static Map<String, Object> map(Object data, String path, int status, String requestId) {
        return map(data, path, status, requestId, null);
    }
 
    public static Map<String, Object> map(Object data, String path, int status, String requestId, String error) {
        Map<String, Object> wrapped = new LinkedHashMap<>();
        wrapped.put("timestamp", OffsetDateTime.now());
        wrapped.put("path", path);
        wrapped.put("status", status);
        if (error != null) {
            wrapped.put("error", error);
        }
        wrapped.put("requestId", requestId);
        wrapped.put("data", (data instanceof Map && ((Map<?, ?>) data).isEmpty()) ? null : data);
        return wrapped;
    }
}
