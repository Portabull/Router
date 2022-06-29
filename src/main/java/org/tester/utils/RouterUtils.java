package org.tester.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.tester.response.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class RouterUtils {

    static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
    }

    @Autowired
    Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(RouterUtils.class);

    public ResponseEntity<?> route(String contextPath,
                                   String requestMapping, HttpServletRequest request) throws ConnectException {

        Map<String, String[]> parameterMap = request.getParameterMap();

        if (parameterMap.get("opCde") == null) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "opCode is mandatory");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        logger.info("OPCode: {}", parameterMap.get("opCde"));

        return execute(contextPath, requestMapping, request);
    }

    public ResponseEntity<?> execute(String contextPath, String requestMapping, HttpServletRequest request) throws ConnectException {
        ResponseEntity<?> response;
        try {
            response = restTemplate.postForEntity(
                    environment.getProperty("base.url") + contextPath + "/" + requestMapping, loadRequest(request), String.class);
        } catch (HttpClientErrorException status) {
            response = new ResponseEntity<>(status.getResponseBodyAsString(), status.getStatusCode());
        } catch (ResourceAccessException ex) {
            response = new ResponseEntity<>(ResponseHandler.prepareResponse(
                    "Server might be down please try after sometime", 503), HttpStatus.SERVICE_UNAVAILABLE);
        }
        return response;
    }

    private HttpEntity loadRequest(HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();

        loadHeaders(request, headers);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        request.getParameterMap().forEach((key, value) ->
                map.put(key, Arrays.asList(value))
        );

        HttpEntity httpEntity = new HttpEntity(map, headers);

        return httpEntity;
    }

    private void loadHeaders(HttpServletRequest request, HttpHeaders headers) {

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames == null)
            return;

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

    }


}
