/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import request.RequestLine;
import utils.FileIoUtils;

import static request.HttpMethod.GET;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class Controller {

    @Request(method = GET, path = {"", "/"})
    public byte[] main(RequestLine requestLine) {
        return "Hello World".getBytes();
    }

    @Request(method = GET, path = "/index.html")
    public byte[] index(RequestLine requestLine) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        } catch (Exception e) {
            throw new RuntimeException("read index file exception: ", e);
        }
    }

    @Request(method = GET, path = "/user/form.html")
    public byte[] userForm(RequestLine requestLine) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        } catch (Exception e) {
            throw new RuntimeException("read index file exception: ", e);
        }
    }

    @Request(method = GET, path = "/create")
    public byte[] userCreate(RequestLine requestLine) {
        return null;
    }
}
