package controller;

import annotation.GetMapping;
import annotation.PostMapping;
import db.DataBase;
import model.ClientResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping(path = "/user")
    public ClientResponse getUserTest() {
        return new ClientResponse(HttpStatus.OK, null, "getUserTest");
    }

    @GetMapping(path = "/user/create")
    public ClientResponse createUserGet(User user) {
        DataBase.addUser(user);
        return new ClientResponse(HttpStatus.OK, null, user);
    }

    @PostMapping(path = "/user/create")
    public ClientResponse createUserPost(User user) {
        DataBase.addUser(user);
        return new ClientResponse(HttpStatus.OK, null, user);
    }

}
