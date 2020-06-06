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
import web.servlet.ModelAndView;

@Controller
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return ModelAndView.from("index");
    }

    @RequestMapping("/user/form.html")
    public ModelAndView userForm() {
        return ModelAndView.from("user/form");
    }

    @RequestMapping(value = "/user/create", method = HttpMethod.POST)
    public ModelAndView signUp(HttpRequest httpRequest) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        logger.info("회원가입 성공 user={}" + user);
        return ModelAndView.from("redirect:/index.html");
    }

    @RequestMapping("/user/login.html")
    public ModelAndView loginView() {
        return ModelAndView.from("user/login");
    }

    @RequestMapping("/user/login_failed.html")
    public ModelAndView loginFailView() {
        return ModelAndView.from("user/login_failed");
    }

    @RequestMapping(value = "/user/login", method = HttpMethod.POST)
    public ModelAndView login(HttpRequest httpRequest, HttpResponse httpResponse) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        User user = DataBase.findUserById(userId);

        if(user == null || !password.equals(user.getPassword())) {
            logger.info("로그인 실패 id={}, pw={}", userId, password);
            return ModelAndView.from("redirect:/user/login_failed.html");
        }

        httpResponse.addCookie(new Cookie("logined", "true"));
        logger.info("로그인 성공 id={}, pw={}", userId, password);
        return ModelAndView.from("redirect:/index.html");
    }

    @RequestMapping("/user/list.html")
    public ModelAndView userListView(HttpRequest httpRequest) {

        Cookie loginedCookie = httpRequest.getCookie("logined");

        if(loginedCookie == null || !"true".equals(loginedCookie.getValue())) {
            return ModelAndView.from("redirect:/user/login.html");
        }

        ModelAndView modelAndView = ModelAndView.from("user/list");
        modelAndView.addAttribute("users", DataBase.findAll());
        return modelAndView;
    }
}
