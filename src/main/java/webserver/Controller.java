/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import model.User;
import org.springframework.util.MultiValueMap;
import request.RequestHeader;
import response.Response;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static request.HttpMethod.GET;
import static request.HttpMethod.POST;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class Controller {

    @RequestMapping(method = GET, path = {"", "/"})
    public Response main(RequestHeader requestHeader) {
        return Response.of("Hello World".getBytes());
    }

    @RequestMapping(method = GET, path = "/index.html")
    public Response index(RequestHeader requestHeader) {
        try {
            return Response.of(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
        } catch (Exception e) {
            throw new RuntimeException("read index file exception: ", e);
        }
    }

    @RequestMapping(method = GET, path = "/user/form.html")
    public Response userForm(RequestHeader requestHeader) {
        try {
            return Response.of(FileIoUtils.loadFileFromClasspath("./templates/user/form.html"));
        } catch (Exception e) {
            throw new RuntimeException("read index file exception: ", e);
        }
    }

    @RequestMapping(method = POST, path = "/user/create")
    public Response userCreate(RequestHeader requestHeader) throws IOException, URISyntaxException {
        MultiValueMap<String, String> bodyMap = requestHeader.getBodyMap();
        User user = new User(bodyMap.getFirst("userId"), bodyMap.getFirst("password"), bodyMap.getFirst("name"), bodyMap.getFirst("email"));
        return Response.redirect(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }
}
