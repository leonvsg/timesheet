package ru.leonvsg.education.timesheet.services;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);

    private Utils() {
    }

    public static String requestParamsToString(HttpServletRequest request){
        if (request == null) return "{}";
        StringBuilder params = new StringBuilder("{ ");
        request.getParameterMap().forEach((key, values)->{
            for (String value: values) params.append(key).append("=").append(value).append(", ");
        });
        params.append("}");
        return params.toString();
    }
}
