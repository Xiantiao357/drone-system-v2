package com.md.basePlatform.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestLoggingInterceptorTest {

    @Test
    void should_allow_request_and_resolve_forwarded_ip() {
        RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/drones");
        request.addHeader("X-Forwarded-For", "203.0.113.1, 70.41.3.18");
        request.setQueryString("page=1");

        boolean allowed = interceptor.preHandle(request, new MockHttpServletResponse(), new Object());

        assertTrue(allowed);
        assertTrue(interceptor.resolveClientIp(request).startsWith("203.0.113.1"));
    }

    @Test
    void should_resolve_remote_addr_when_no_forwarded_header() {
        RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");

        assertTrue(interceptor.resolveClientIp(request).contains("127.0.0.1"));
    }
}
