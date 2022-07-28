package controller;

import annotation.GetMapping;
import annotation.PostMapping;
import db.DataBase;
import model.ClientResponse;
import model.Credential;
import model.User;
import org.springframework.http.HttpStatus;
import service.UserService;

public class UserController {

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
        return UserService.getInstance().createUser(user);
    }

    @PostMapping(path = "/user/login")
    public ClientResponse login(Credential credential) {
        return UserService.getInstance().auth(credential);
    }

}
