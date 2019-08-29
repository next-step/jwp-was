package controller;

import annotation.Controller;
import annotation.RequestBody;
import annotation.RequestMapping;
import db.DataBase;
import model.User;
import model.controller.ResponseEntity;
import model.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create")
    public ResponseEntity createUser(@RequestBody User user) {
        logger.debug("==============GET /user/create");
        logger.debug(user.toString());
        DataBase.addUser(user);
        return ResponseEntity.of("/user/profile");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public ResponseEntity createUserPost(@RequestBody User user) {
        logger.debug("==============POST /user/create");
        logger.debug(user.toString());
        DataBase.addUser(user);
        return ResponseEntity.of("/user/profile");
    }

    @RequestMapping(method = HttpMethod.PUT, path = "/user/update")
    public ResponseEntity updateUser(@RequestBody User user) {
        logger.debug("==============PUT /user/update");
        logger.debug(user.toString());
        User userFound = DataBase.findUserById(user.getUserId());
        userFound.update(user);
        return ResponseEntity.of("redirect:/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/login")
    public ResponseEntity loginUser(@RequestBody User user) {
        logger.debug("==============POST /user/login");
        logger.debug(user.toString());
        User userFound = DataBase.findUserById(user.getUserId());
        if (userFound != null && userFound.matchPassword(user.getPassword())) {
            return ResponseEntity.of("redirect:/index.html", true);
        } else {
            return ResponseEntity.of("redirect:/user/login_failed.html");
        }
    }
}
