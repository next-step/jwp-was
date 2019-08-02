/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import request.HttpMethod;
import request.RequestLine;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class RequestMappingHandler {

    private Table<HttpMethod, String, Method> controllerBean = HashBasedTable.create(); //httpMethod(key1), path(key2), method(value)

    public RequestMappingHandler(Controller controller) {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            Request request = method.getAnnotationsByType(Request.class)[0];
            Arrays.stream(request.path()).forEach(path -> controllerBean.put(request.method(), path, method));
        }
    }

    public byte[] getBody(RequestLine requestLine) {
        return new byte[0];
    }
}
