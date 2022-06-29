package org.tester.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tester.utils.RouterUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RouterController {

    private static final Logger logger = LoggerFactory.getLogger(RouterController.class);

    @Autowired
    private RouterUtils routerUtils;

    @PostMapping("/router/{contextPath}/{requestMapping}")
    public ResponseEntity<?> router(HttpServletRequest request,
                                    @PathVariable(required = true) String contextPath,
                                    @PathVariable(required = true) String requestMapping) throws ConnectException {
        return routerUtils.route(contextPath, requestMapping, request);
    }

    @PostMapping("/testContext/textMapping")
    public ResponseEntity<?> test(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            logger.info("Key : {} , Value : {}", entry.getKey(), entry.getValue());
        }

        Map<String,Object> data = new LinkedHashMap<>();
        Map<String,Object> data1 = new LinkedHashMap<>();

        data1.put("custData","ct");
        data1.put("custDa1ta",12);




        data.put("custData","ct");
        data.put("custDa1ta",12);
        data.put("custDa1t11a",data1);

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

}
