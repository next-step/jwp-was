/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import model.User;
import org.springframework.util.MultiValueMap;
import request.RequestHeader;
import utils.FileIoUtils;

import static request.HttpMethod.GET;
import static request.HttpMethod.POST;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class Controller {

    @Request(method = GET, path = {"", "/"})
    public byte[] main(RequestHeader requestHeader) {
        return "Hello World".getBytes();
    }

    @Request(method = GET, path = "/index.html")
    public byte[] index(RequestHeader requestHeader) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        } catch (Exception e) {
            throw new RuntimeException("read index file exception: ", e);
        }
    }

    @Request(method = GET, path = "/user/form.html")
    public byte[] userForm(RequestHeader requestHeader) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        } catch (Exception e) {
            throw new RuntimeException("read index file exception: ", e);
        }
    }

    @Request(method = POST, path = "/user/create")
    public byte[] userCreate(RequestHeader requestHeader) {
        MultiValueMap<String, String> bodyMap = requestHeader.getBodyMap();
        User user = new User(bodyMap.getFirst("userId"), bodyMap.getFirst("password"), bodyMap.getFirst("name"), bodyMap.getFirst("email"));
        return null;
    }
}
