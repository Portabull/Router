package org.tester.service.impl;

import org.springframework.context.annotation.Primary;
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
@Primary
public class RequestServiceImpl implements RequestService {

    @Override
    public HttpEntity loadRequest(String url, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();

        loadHeaders(request, headers);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        request.getParameterMap().forEach((key, value) -> map.put(key, Arrays.asList(value)));

        HttpEntity httpEntity = new HttpEntity(map, headers);

        return httpEntity;
    }

    private void loadHeaders(HttpServletRequest request, HttpHeaders headers) {

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames == null) return;

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

    }

}
