package controller;

import annotation.GetMapping;

public class UserController {

    @GetMapping(path = "/user")
    public String getUserTest() {
        return "getUserReturnValue";
    }

}
