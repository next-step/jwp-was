package webserver.adapter.in.controller;

import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.HttpSession;
import webserver.domain.http.RequestBody;
import webserver.domain.user.User;

import java.io.IOException;
import java.util.Objects;

public class LoginController extends AbstractController {

    private final UserProcessor userProcessor;

    public LoginController(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        RequestBody body = httpRequest.getRequestBody();
        User user = userProcessor.findUser(body.get("userId"));

        if (Objects.nonNull(user) && user.verifyPassword(body.get("password"))) {
            HttpSession session = httpRequest.getHttpHeader().getCookies().getSession();
            session.setAttribute("user", user);
            httpResponse.responseCookiesAndSession("/index.html", true, session.getId());
            return;
        }

        httpResponse.responseCookies("/user/login_failed.html", false);
    }
}
