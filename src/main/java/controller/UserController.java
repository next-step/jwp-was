package controller;

import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping(path = "/user/create")
    public String createUser() {
        return "/user/form";
    }
}
