package controller;

import annotations.Controller;
import annotations.RequestMapping;
import db.DataBase;
import http.Cookie;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import web.sevlet.View;
import web.sevlet.view.HtmlView;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("/index.html")
    public View index() {
        return new HtmlView("./templates/index.html");
    }

    @RequestMapping("/user/form.html")
    public View userForm() {
        return new HtmlView("./templates/user/form.html");
    }

    @RequestMapping(value = "/user/create", method = HttpMethod.POST)
    public View signUp(HttpRequest httpRequest) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        logger.info("회원가입 성공 user={}" + user);
        return new HtmlView("redirect:/index.html");
    }

    @RequestMapping("/user/login.html")
    public View loginView() {
        return new HtmlView("./templates/user/login.html");
    }

    @RequestMapping("/user/login_failed.html")
    public View loginFailView() {
        return new HtmlView("./templates/user/login_failed.html");
    }

    @RequestMapping(value = "/user/login", method = HttpMethod.POST)
    public View login(HttpRequest httpRequest, HttpResponse httpResponse) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        User user = DataBase.findUserById(userId);

        if(user == null || !password.equals(user.getPassword())) {
            logger.info("로그인 실패 id={}, pw={}", userId, password);
            return new HtmlView("redirect:/user/login_failed.html");
        }

        httpResponse.addCookie(new Cookie("logined", "true"));
        logger.info("로그인 성공 id={}, pw={}", userId, password);
        return new HtmlView("redirect:/index.html");
    }

    @RequestMapping("/user/list.html")
    public View userListView(HttpRequest httpRequest) {

        Cookie loginedCookie = httpRequest.getCookie("logined");

        if(loginedCookie == null || !"true".equals(loginedCookie.getValue())) {
            return new HtmlView("redirect:/user/login.html");
        }
        return new HtmlView("./templates/user/list.html");
    }
}
