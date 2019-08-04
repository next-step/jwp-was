/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.springframework.util.MultiValueMap;
import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static request.HttpMethod.GET;
import static request.HttpMethod.POST;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class Controller {

    @RequestMapping(method = GET, path = {"", "/"})
    public HttpResponse main(HttpRequest httpRequest) {
        return HttpResponse.success("Hello World".getBytes());
    }

    @RequestMapping(method = GET, path = "/index.html")
    public HttpResponse index(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.success(createOutputStream("./templates/index.html"));
    }

    @RequestMapping(method = GET, path = "/user/form.html")
    public HttpResponse userForm(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.success(createOutputStream("./templates/user/form.html"));
    }

    @RequestMapping(method = GET, path = "/user/login.html")
    public HttpResponse userLoginForm(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.success(createOutputStream("./templates/user/login.html"));
    }

    @RequestMapping(method = POST, path = "/user/create")
    public HttpResponse userCreate(HttpRequest httpRequest) throws IOException, URISyntaxException {
        MultiValueMap<String, String> bodyMap = httpRequest.getBodyMap();
        User user = new User(bodyMap.getFirst("userId"), bodyMap.getFirst("password"), bodyMap.getFirst("name"), bodyMap.getFirst("email"));
        DataBase.addUser(user);
        return HttpResponse.redirect(createOutputStream("./templates/index.html"), "http://localhost:8080/index.html");
    }

    @RequestMapping(method = POST, path = "/user/login")
    public HttpResponse userLogin(HttpRequest httpRequest) {
        MultiValueMap<String, String> bodyMap = httpRequest.getBodyMap();
        User user = DataBase.findUserById(bodyMap.getFirst("userId"));

        if (user == null ) {
            return HttpResponse.loginFail("user not exist".getBytes());
        } else if (Objects.equals(bodyMap.getFirst("password"), bodyMap.getFirst("password"))) {
            return HttpResponse.loginFail("password not matched".getBytes());
        }

        return HttpResponse.loginSuccess("login success".getBytes());
    }

    @RequestMapping(method = GET, path = "/user/list.html")
    public HttpResponse userList(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (!httpRequest.getCookie().isLogined()) {
            return HttpResponse.redirect(createOutputStream("./templates/index.html"), "http://localhost:8080/index.html");
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        String profilePage = template.apply(DataBase.findAll());

        return HttpResponse.success(profilePage.getBytes());
    }

    private byte[] createOutputStream(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }
}
