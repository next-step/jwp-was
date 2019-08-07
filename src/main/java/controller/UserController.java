package controller;

import annotation.Controller;
import annotation.RequestBody;
import annotation.RequestMapping;
import model.User;

@Controller
public class UserController {
    @RequestMapping(path = "/user/create")
    public String createUser(@RequestBody User user) {
        return "/user/form";
    }
}
