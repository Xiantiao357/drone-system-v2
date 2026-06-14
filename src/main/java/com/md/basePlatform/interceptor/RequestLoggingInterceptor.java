package com.md.basePlatform.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 请求日志拦截器，打印 URI、Method、IP 与参数.
 */
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String queryString = request.getQueryString();
        String params = queryString != null ? queryString : Collections.list(request.getParameterNames())
                .stream()
                .map(name -> name + "=" + request.getParameter(name))
                .collect(Collectors.joining("&"));

        LOGGER.info("Request: method={}, uri={}, ip={}, params={}",
                request.getMethod(),
                request.getRequestURI(),
                resolveClientIp(request),
                params.isEmpty() ? "-" : params);
        return true;
    }

    /**
     * 解析客户端 IP.
     *
     * @param request HTTP 请求
     * @return 客户端 IP
     */
    String resolveClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
