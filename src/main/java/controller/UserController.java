package controller;

import annotation.GetMapping;
import annotation.PostMapping;
import db.DataBase;
import model.HttpHeaders;
import model.HttpResponseMessage;
import model.Credential;
import model.User;
import service.UserService;
import types.HttpStatus;

import java.io.IOException;

public class UserController {

    @GetMapping(path = "/user")
    public HttpResponseMessage getUserTest() throws IOException {
        return new HttpResponseMessage(HttpStatus.OK, null, "getUserTest");
    }

    @GetMapping(path = "/user/create")
    public HttpResponseMessage createUserGet(User user) throws IOException {
        DataBase.addUser(user);
        return new HttpResponseMessage(HttpStatus.OK, null, user);
    }

    @PostMapping(path = "/user/create")
    public HttpResponseMessage createUserPost(User user) throws IOException {
        return UserService.getInstance().createUser(user);
    }

    @PostMapping(path = "/user/login")
    public HttpResponseMessage login(HttpHeaders requestHeaders, Credential credential) throws IOException {
        return UserService.getInstance().auth(requestHeaders, credential);
    }

    @GetMapping(path = "/user/list")
    public HttpResponseMessage getUsers() throws IOException {
        return UserService.getInstance().getUsers();
    }

}
