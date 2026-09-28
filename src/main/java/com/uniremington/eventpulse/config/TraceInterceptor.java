package com.uniremington.eventpulse.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class TraceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);
    private static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        request.setAttribute(TRACE_HEADER, traceId);
        response.addHeader(TRACE_HEADER, traceId);
        logger.info("[TRAZA {}] Solicitud recibida: {} {}", traceId, request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Object traceId = request.getAttribute(TRACE_HEADER);
        logger.info("[TRAZA {}] Respuesta emitida: Estado HTTP {}", traceId, response.getStatus());
    }
}