package webserver.ui;

import model.dto.UserLoginRequest;
import model.dto.UserSaveRequest;
import org.springframework.http.HttpMethod;
import webserver.application.UserService;
import webserver.domain.Cookie;
import webserver.domain.DefaultView;
import webserver.domain.HttpHeaders;
import webserver.domain.HttpRequest;
import webserver.domain.HttpSession;
import webserver.domain.RequestBody;
import webserver.domain.RequestMapping;
import webserver.domain.ResponseBody;
import webserver.domain.ResponseEntity;
import webserver.domain.SessionManager;
import webserver.domain.TemplateView;

import java.util.Collections;
import java.util.Objects;

import static webserver.domain.HttpSession.SESSION_COOKIE_NAME;

/**
 * 유저 정보 컨트롤러
 */
public class UserController implements Controller {
    public static final String LOGINED = "logined";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = {HttpMethod.GET, HttpMethod.POST})
    @ResponseBody
    public ResponseEntity<HttpRequest> usersTestApi(HttpRequest httpRequest) {
        RequestBody body = httpRequest.getRequestBody();
        HttpHeaders headers = httpRequest.getHeaders();
        if (!body.isEmpty()) {
            headers.add(HttpHeaders.CONTENT_LENGTH, httpRequest.toString().length() + "");
        }

        return ResponseEntity.ok().jsonHeader().body(httpRequest);
    }

    @RequestMapping(value = "/user/form.html", method = HttpMethod.GET)
    public ResponseEntity<DefaultView> userForm(HttpRequest httpRequest) {
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
        if (isLogin(httpRequest)) {
            return ResponseEntity.found("/index.html").build();
        }

        DefaultView view = DefaultView.createDefaultHtmlView("/user/login");

        return ResponseEntity.ok().htmlHeader().body(view);
    }

    @RequestMapping(value = "/user/login", method = {HttpMethod.POST})
    public ResponseEntity<?> login(HttpRequest httpRequest) {
        RequestBody requestBody = httpRequest.getRequestBody();
        boolean loginResult = userService.login(UserLoginRequest.from(requestBody));

        HttpSession session = httpRequest.getSession();
        session.setAttribute(LOGINED, loginResult);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, session.getId());
        cookie.setPath("/");

        if (loginResult) {
            return ResponseEntity.found("/index.html").cookie(cookie).build();
        }

        return ResponseEntity.found("/user/login_failed.html").cookie(cookie).build();
    }

    @RequestMapping(value = "/user/list.html", method = HttpMethod.GET)
    public ResponseEntity<TemplateView> userList(HttpRequest httpRequest) {

        if (isLogin(httpRequest)) {
            DefaultView view = TemplateView.createDefaultHtmlView("/user/list",
                    Collections.singletonMap("users", userService.findAllUsers()));

            return ResponseEntity.ok().htmlHeader().body(view);
        }
        return ResponseEntity.found("/user/login_failed.html").build();
    }

    private boolean isLogin(HttpRequest httpRequest) {
        if (Objects.isNull(httpRequest) || !httpRequest.hasCookie()) {
            return false;
        }
        return (boolean) httpRequest.getSession().getAttribute(LOGINED);
    }


}
