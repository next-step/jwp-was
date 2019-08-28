package controller;

import annotation.Controller;
import annotation.RequestBody;
import annotation.RequestMapping;
import db.DataBase;
import model.User;
import model.controller.View;
import model.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create")
    public View createUser(@RequestBody User user) {
        logger.debug("==============GET /user/create");
        logger.debug(user.toString());
        DataBase.addUser(user);
        return View.of("/user/profile");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public View createUserPost(@RequestBody User user) {
        logger.debug("==============POST /user/create");
        logger.debug(user.toString());
        DataBase.addUser(user);
        return View.of("/user/profile");
    }

    @RequestMapping(method = HttpMethod.PUT, path = "/user/update")
    public View updateUser(@RequestBody User user) {
        logger.debug("==============PUT /user/update");
        logger.debug(user.toString());
        User userFound = DataBase.findUserById(user.getUserId());
        userFound.update(user);
        return View.of("redirect:/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/login")
    public View loginUser(@RequestBody User user) {
        logger.debug("==============POST /user/login");
        logger.debug(user.toString());
        User userFound = DataBase.findUserById(user.getUserId());
        if (userFound != null && userFound.matchPassword(user.getPassword())) {
            return View.of("redirect:/index.html", true);
        } else {
            return View.of("redirect:/user/login_failed.html");
        }
    }
}
