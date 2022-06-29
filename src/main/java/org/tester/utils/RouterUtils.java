package org.tester.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.tester.response.ResponseHandler;
import org.tester.service.RequestService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.util.Map;


@Service
public class RouterUtils {

    static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
    }

    @Autowired
    Environment environment;

    static String baseUrl;

    @Autowired
    @Qualifier("loadRequestService")
    RequestService requestService;

    @PostConstruct
    public void loadEnv() {
        baseUrl = environment.getProperty("base.url");
        logger.info("Base Url Loaded: {}", baseUrl);
    }


    private static final Logger logger = LoggerFactory.getLogger(RouterUtils.class);

    public ResponseEntity<?> route(String contextPath, String requestMapping, HttpServletRequest request) throws ConnectException {

        Map<String, String[]> parameterMap = request.getParameterMap();

        if (parameterMap.get("opCde") == null) {
            return new ResponseEntity<>(ResponseHandler.prepareResponse("opCode is mandatory", 400), HttpStatus.BAD_REQUEST);
        }

        logger.info("OPCode: {}", parameterMap.get("opCde"));

        return execute(contextPath, requestMapping, request);
    }

    public ResponseEntity<?> execute(String contextPath, String requestMapping, HttpServletRequest request) throws ConnectException {
        ResponseEntity<?> response;
        try {

            String url = new StringBuilder(baseUrl).append(contextPath).append("/").append(requestMapping).toString();

            response = restTemplate.postForEntity(url, requestService.loadRequest(url, request), String.class);

        } catch (HttpClientErrorException status) {

            response = new ResponseEntity<>(status.getResponseBodyAsString(), status.getStatusCode());

        } catch (ResourceAccessException ex) {

            response = new ResponseEntity<>(ResponseHandler.prepareResponse(
                    "Server might be down please try after sometime", 503), HttpStatus.SERVICE_UNAVAILABLE);

        }
        return response;
    }
}
