/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.handler;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import request.HttpMethod;
import request.RequestHeader;
import request.RequestLine;
import response.Response;
import webserver.Controller;
import webserver.RequestMapping;

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
            RequestMapping requestMapping = method.getAnnotationsByType(RequestMapping.class)[0];
            Arrays.stream(requestMapping.path()).forEach(path -> controllerBean.put(requestMapping.method(), path, method));
        }
    }

    @Override
    public Response request(RequestHeader requestHeader) {
        RequestLine requestLine = requestHeader.getRequestLine();
        Method method = controllerBean.get(requestLine.getMethod(), requestLine.getPath());
        try {
            if (method == null) {
                throw new RuntimeException("There are not exist request path.");
            }
            return (Response) method.invoke(controller, requestHeader);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("RequestMappingHandler getBody failed: ", e);
        }
    }

    @Override
    public boolean isSupport(RequestHeader requestHeader) {
        RequestLine requestLine = requestHeader.getRequestLine();
        return controllerBean.contains(requestLine.getMethod(), requestLine.getPath());
    }
}
