package webserver.ui;

import model.dto.UserLoginRequest;
import model.dto.UserSaveRequest;
import org.springframework.http.HttpMethod;
import webserver.application.UserService;
import webserver.domain.Cookie;
import webserver.domain.DefaultView;
import webserver.domain.HttpRequest;
import webserver.domain.RequestBody;
import webserver.domain.RequestMapping;
import webserver.domain.ResponseBody;
import webserver.domain.ResponseEntity;
import webserver.domain.TemplateView;

import java.io.IOException;
import java.util.Collections;

public class UserController implements Controller {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = {HttpMethod.GET})
    @ResponseBody
    public ResponseEntity<HttpRequest> usersTestApi(HttpRequest httpRequest) {
        return ResponseEntity.ok().jsonHeader().body(httpRequest);
    }

    @RequestMapping(value = "/user/form.html", method = HttpMethod.GET)
    public ResponseEntity<DefaultView> getUsers(HttpRequest httpRequest) {
        DefaultView view = DefaultView.createDefaultHtmlView("/user/form");

        return ResponseEntity.ok().htmlHeader().body(view);
    }

    @RequestMapping(value = "/user/login_failed.html", method = HttpMethod.GET)
    public ResponseEntity<DefaultView> loginFailed(HttpRequest httpRequest) {
        DefaultView view = DefaultView.createDefaultHtmlView("/user/login_failed");

        return ResponseEntity.ok().htmlHeader().body(view);
    }

    @RequestMapping(value = "/user/create", method = {HttpMethod.GET, HttpMethod.POST})
    public ResponseEntity<DefaultView> createUser(HttpRequest httpRequest) {
        RequestBody requestBody = httpRequest.getRequestBody();

        userService.createUser(UserSaveRequest.from(requestBody));

        return ResponseEntity.created("/index.html").body(DefaultView.createDefaultHtmlView("/index"));
    }

    @RequestMapping(value = "/user/login.html", method = {HttpMethod.GET})
    public ResponseEntity<DefaultView> loginHtml(HttpRequest httpRequest) {
        DefaultView view = DefaultView.createDefaultHtmlView("/user/login");

        return ResponseEntity.ok().htmlHeader().body(view);
    }

    @RequestMapping(value = "/user/login", method = {HttpMethod.POST})
    public ResponseEntity<?> login(HttpRequest httpRequest) {
        RequestBody requestBody = httpRequest.getRequestBody();
        boolean loginResult = userService.login(UserLoginRequest.from(requestBody));

        Cookie cookie = new Cookie();
        cookie.addAttribute("Path", "/");
        cookie.addAttribute("logined", String.valueOf(loginResult));

        if (loginResult) {
            return ResponseEntity.found("/index.html").cookie(cookie).build();
        }

        return ResponseEntity.found("/user/login_failed").cookie(cookie).build();
    }

    @RequestMapping(value = "/user/list.html", method = HttpMethod.GET)
    public ResponseEntity<TemplateView> userList(HttpRequest httpRequest) throws IOException {

        if (isLogin(httpRequest)) {
            DefaultView view = TemplateView.createDefaultHtmlView("/user/list",
                    Collections.singletonMap("users", userService.findAllUsers()));

            return ResponseEntity.ok().htmlHeader().body(view);
        }
        return ResponseEntity.found("/user/login_failed.html").build();
    }

    private boolean isLogin(HttpRequest httpRequest) {
        Cookie cookie = httpRequest.getCookie();
        String loginedStr = cookie.getAttribute("logined");
        return Boolean.getBoolean(loginedStr);
    }


}
