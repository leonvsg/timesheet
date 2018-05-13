package ru.leonvsg.education.timesheet.services;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    private Utils() {
    }

    public static String requestParamsToString(HttpServletRequest request){
        if (request == null) return "{}";
        StringBuilder params = new StringBuilder("{");
        request.getParameterMap().forEach((key, values)->{
            for (String value: values) params.append(key + ":" + value + ";");
        });
        params.append("}");
        return params.toString();
    }
}
