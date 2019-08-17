package controller;

import annotation.Controller;
import annotation.RequestBody;
import annotation.RequestMapping;
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
        return View.of("/user/profile");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public View createUserPost(@RequestBody User user) {
        logger.debug("==============POST /user/create");
        logger.debug(user.toString());
        return View.of("/user/profile");
    }

    @RequestMapping(method = HttpMethod.PUT, path = "/user/update")
    public View updateUser(@RequestBody User user) {
        logger.debug("==============PUT /user/update");
        logger.debug(user.toString());
        return View.of("redirect:/index.html");
    }
}
