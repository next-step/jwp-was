package webserver.ui;

import model.dto.UserLoginRequest;
import model.dto.UserResponse;
import model.dto.UserSaveRequest;
import org.springframework.http.HttpMethod;
import webserver.application.UserService;
import webserver.domain.Cookie;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatus;
import webserver.domain.RequestBody;
import webserver.domain.RequestMapping;
import webserver.domain.TemplateEngineHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UserController implements Controller {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/form.html", method = HttpMethod.GET)
    public HttpResponse getUsers(HttpRequest httpRequest) {

        return HttpResponse.templateResponse("/user/form.html");
    }

    @RequestMapping(value = "/user/login_failed.html", method = HttpMethod.GET)
    public HttpResponse loginFailed(HttpRequest httpRequest) {
        return HttpResponse.templateResponse("/user/login_failed.html");
    }

    @RequestMapping(value = "/user/create", method = {HttpMethod.GET, HttpMethod.POST})
    public HttpResponse createUser(HttpRequest httpRequest) {
        HttpResponse response = new HttpResponse(HttpStatus.FOUND, null, null);
        RequestBody requestBody = httpRequest.getRequestBody();

        userService.createUser(UserSaveRequest.from(requestBody));
        response.addHeader("Location", "/index.html");

        return response;
    }

    @RequestMapping(value = "/user/login.html", method = {HttpMethod.GET})
    public HttpResponse loginHtml(HttpRequest httpRequest) {

        return HttpResponse.templateResponse("/user/login.html");
    }
    @RequestMapping(value = "/user/login", method = {HttpMethod.POST})
    public HttpResponse login(HttpRequest httpRequest) {
        HttpResponse response;
        RequestBody requestBody = httpRequest.getRequestBody();
        boolean loginResult = userService.login(UserLoginRequest.from(requestBody));

        Cookie cookie = new Cookie();
        cookie.addAttribute("Path", "/");
        cookie.addAttribute("logined", String.valueOf(loginResult));

        if (loginResult) {
            response = HttpResponse.of(HttpStatus.OK, "/index.html");
            response.addHeader("Location", "/index.html");
            response.addCookie(cookie);
            return response;
        }

        response = HttpResponse.of(HttpStatus.OK, "/user/login_failed.html");
        response.addHeader("Location", "/index.html");
        response.addCookie(cookie);
        return response;
    }

    @RequestMapping(value = "/user/list.html", method = HttpMethod.GET)
    public HttpResponse userList(HttpRequest httpRequest) throws IOException {
        if (isLogin(httpRequest)) {
            return HttpResponse.templateResponse("/user/login.html");
        }
        return HttpResponse.templateResponse("/user/list",
                Collections.singletonMap("users", userService.findAllUsers()));
    }

    private boolean isLogin(HttpRequest httpRequest) {
        Cookie cookie = httpRequest.getCookie();
        String loginedStr = cookie.getAttribute("logined");
        return Boolean.getBoolean(loginedStr);
    }


}
