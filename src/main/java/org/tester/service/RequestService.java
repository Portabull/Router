package org.tester.service;

import org.springframework.http.HttpEntity;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {

    public HttpEntity loadRequest(String url, HttpServletRequest request);

}
