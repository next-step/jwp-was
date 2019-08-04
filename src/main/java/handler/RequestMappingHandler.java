/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package handler;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import request.HttpMethod;
import request.HttpRequest;
import request.RequestLine;
import response.HttpResponse;
import controller.Controller;
import controller.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestMappingHandler implements RequestStrategy {

    private Controller controller;
    private Table<HttpMethod, String, Method> controllerBean = HashBasedTable.create(); //httpMethod(key1), path(key2), method(value)

    public RequestMappingHandler(Controller controller) {
        this.controller = controller;
        for (Method method : controller.getClass().getDeclaredMethods()) {
            RequestMapping[] requestMappings = method.getAnnotationsByType(RequestMapping.class);
            if (requestMappings.length > 0) {
                RequestMapping requestMapping = requestMappings[0];
                Arrays.stream(requestMapping.path()).forEach(path -> controllerBean.put(requestMapping.method(), path, method));
            }
        }
    }

    @Override
    public HttpResponse request(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Method method = controllerBean.get(requestLine.getMethod(), requestLine.getPath());
        try {
            if (method == null) {
                throw new RuntimeException("There are not exist request path.");
            }
            return (HttpResponse) method.invoke(controller, httpRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("RequestMappingHandler getBody failed: ", e);
        }
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        return controllerBean.contains(requestLine.getMethod(), requestLine.getPath());
    }
}
