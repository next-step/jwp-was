/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import request.HttpMethod;
import request.RequestHeader;
import request.RequestLine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestMappingHandler implements RequestMapping {

    private Controller controller;
    private Table<HttpMethod, String, Method> controllerBean = HashBasedTable.create(); //httpMethod(key1), path(key2), method(value)

    public RequestMappingHandler(Controller controller) {
        this.controller = controller;
        for (Method method : controller.getClass().getDeclaredMethods()) {
            Request request = method.getAnnotationsByType(Request.class)[0];
            Arrays.stream(request.path()).forEach(path -> controllerBean.put(request.method(), path, method));
        }
    }

    @Override
    public byte[] getBody(RequestHeader requestHeader) {
        RequestLine requestLine = requestHeader.getRequestLine();
        Method method = controllerBean.get(requestLine.getMethod(), requestLine.getPath());
        try {
            return (byte[]) method.invoke(controller, requestHeader);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("RequestMappingHandler getBody failed: ", e);
        }
    }
}
