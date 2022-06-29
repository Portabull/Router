package org.tester.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static Map<String, Object> prepareResponse(String message, int statusCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", statusCode != 200 ? "FAILED" : "SUCCESS");
        response.put("statusCode", statusCode);
        return response;
    }

}
