package com.example.springCloud.util;

import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdUtil {
    public static final String TRACE_ID = "requestId";

    public static String getTraceId() {
        String traceId = (String) MDC.get(TRACE_ID);
        return traceId == null ? "" : traceId;
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void remove() {
        MDC.remove(TRACE_ID);

    }

    public static void clear() {
        MDC.clear();
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
