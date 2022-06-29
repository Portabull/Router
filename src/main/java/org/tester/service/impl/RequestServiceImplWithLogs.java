package org.tester.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.tester.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Service
public class RequestServiceImplWithLogs implements RequestService {

    static final Logger logger = LoggerFactory.getLogger(RequestServiceImplWithLogs.class);

    @Override
    public HttpEntity loadRequest(String url, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();

        loadHeaders(request, headers);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        System.out.println("\r\n");

        logger.info("************************* Request Info Starts *************************");

        request.getParameterMap().forEach((key, value) -> {
            logger.info("Key : {} , Value : {}", key, value);
            map.put(key, Arrays.asList(value));
        });

        logger.info("************************* Request Info Ends *************************");

        System.out.println("\r\n");

        HttpEntity httpEntity = new HttpEntity(map, headers);

        return httpEntity;
    }

    private void loadHeaders(HttpServletRequest request, HttpHeaders headers) {
        Enumeration<String> headerNames = request.getHeaderNames();

        System.out.println("\r\n\r\r");

        logger.info("************************* Header Info Starts *************************");

        if (headerNames == null) {
            logger.info("************************* Header Info Ends *************************");
            return;
        }

        while (headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            String headerValue = request.getHeader(headerKey);
            logger.info("Key : {} , Value : {}", headerKey, headerValue);
            headers.set(headerKey, headerValue);
        }

        logger.info("************************* Header Info Ends *************************");

        System.out.println("\r\n");
    }
}
