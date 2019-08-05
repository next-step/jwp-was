package controller;

import db.DataBase;
import exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.Query;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
public class UserController implements Controller {

    private static UserController userController;

    private static final Map<RequestMapping, BiFunction<HttpRequest, HttpResponse, String>> controller = new HashMap<>();

    static {
        controller.put(new RequestMapping("/", HttpMethod.GET), index());
        controller.put(new RequestMapping("/index", HttpMethod.GET), index());
        controller.put(new RequestMapping("/user/form", HttpMethod.GET), createForm());
        controller.put(new RequestMapping("/user/create", HttpMethod.POST), createUser());
        controller.put(new RequestMapping("/user/login", HttpMethod.GET), loginForm());
        controller.put(new RequestMapping("/user/login", HttpMethod.POST), login());
    }

    private static BiFunction<HttpRequest, HttpResponse, String> index() {
        return (request, response) -> "index";
    }

    private static BiFunction<HttpRequest, HttpResponse, String> createForm() {
        return (request, response) -> "/user/form";
    }

    private static BiFunction<HttpRequest, HttpResponse, String> createUser() {
        return (request, response) -> {
            Query query = request.query();
            User user = User.builder()
                    .userId(query.get("userId"))
                    .password(query.get("password"))
                    .name(query.get("name"))
                    .email(query.get("email"))
                    .build();

            DataBase.addUser(user);
            log.info("user object : {}", user);

            return "redirect:/index";
        };
    }

    private static BiFunction<HttpRequest, HttpResponse, String> loginForm() {
        return (request, response) -> "/user/login";
    }

    private static BiFunction<HttpRequest, HttpResponse, String> login() {
        return (request, response) -> {
            Query query = request.query();
            final String userId = query.get("userId");
            final String password = query.get("password");

            User found = DataBase.findUserById(userId);
            if (found == null || !found.authenticate(userId, password)) {
                response.put("Set-Cookie", "logined=false; Path=/");
                return "/user/login_failed";
            }

            response.put("Set-Cookie", "logined=true; Path=/");
            return "/index";
        };
    }

    @Override
    public String get(HttpRequest request, HttpResponse response) {
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
