package controller;

import db.DataBase;
import exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import view.ModelAndView;
import webserver.http.HttpMethod;
import webserver.http.cookie.SimpleCookie;
import webserver.http.request.HttpRequest;
import webserver.http.request.Query;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
public class UserController implements Controller {

    private static UserController userController;

    private static final Map<RequestMapping, BiFunction<HttpRequest, HttpResponse, ModelAndView>> controller = new HashMap<>();

    static {
        controller.put(new RequestMapping("/", HttpMethod.GET), index());
        controller.put(new RequestMapping("/index", HttpMethod.GET), index());
        controller.put(new RequestMapping("/user/form", HttpMethod.GET), createForm());
        controller.put(new RequestMapping("/user/create", HttpMethod.POST), createUser());
        controller.put(new RequestMapping("/user/login", HttpMethod.GET), loginForm());
        controller.put(new RequestMapping("/user/login", HttpMethod.POST), login());
        controller.put(new RequestMapping("/user/list", HttpMethod.GET), userList());
    }

    private static BiFunction<HttpRequest, HttpResponse, ModelAndView> index() {
        return (request, response) -> new ModelAndView("index");
    }

    private static BiFunction<HttpRequest, HttpResponse, ModelAndView> createForm() {
        return (request, response) -> new ModelAndView("/user/form");
    }

    private static BiFunction<HttpRequest, HttpResponse, ModelAndView> createUser() {
        return (request, response) -> {
            Query query = request.query();

            if (!query.isEmpty()) {
                User user = User.builder()
                        .userId(query.get("userId"))
                        .password(query.get("password"))
                        .name(query.get("name"))
                        .email(query.get("email"))
                        .build();

                DataBase.addUser(user);
                log.info("user object : {}", user);
            }

            return new ModelAndView("redirect:/index");
        };
    }

    private static BiFunction<HttpRequest, HttpResponse, ModelAndView> loginForm() {
        return (request, response) -> new ModelAndView("/user/login");
    }

    private static BiFunction<HttpRequest, HttpResponse, ModelAndView> login() {
        return (request, response) -> {
            Query query = request.query();
            final String userId = query.get("userId");
            final String password = query.get("password");

            User found = DataBase.findUserById(userId);
            if (found == null || !found.authenticate(userId, password)) {
                response.setCookie("logined=false; Path=/");
                return new ModelAndView("/user/login_failed");
            }

            response.setCookie("logined=true; Path=/");
            return new ModelAndView("/index");
        };
    }

    private static BiFunction<HttpRequest, HttpResponse, ModelAndView> userList() {
        return (request, response) -> {
            final SimpleCookie cookie = (SimpleCookie) request.getCookie();
            final boolean logined = Boolean.valueOf(cookie.get("logined"));
            if (!logined) {
                return new ModelAndView("/user/login");
            }

            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());

            return new ModelAndView("/user/list", model);
        };
    }

    // todo 리팩토링 할 때 다른 클래스로 분리할 것
    @Override
    public ModelAndView get(HttpRequest request, HttpResponse response) {
        try {
            RequestMapping requestMapping = new RequestMapping(
                    request.removeExtensionPath(),
                    request.httpMethod());

            return controller.get(requestMapping)
                             .apply(request, response);
        } catch (NullPointerException e) {
            throw new NotFoundException(String.format("경로를 찾을 수 없습니다. 입력값 : %s", request.path()));
        }
    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }

        return userController;
    }

    private UserController() {}
}
