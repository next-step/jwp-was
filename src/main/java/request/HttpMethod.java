/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package request;

import java.util.Arrays;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod of(String line) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equalsIgnoreCase(line))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not accepted httpMethod"));
    }
}
