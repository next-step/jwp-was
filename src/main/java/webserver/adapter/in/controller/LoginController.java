package webserver.adapter.in.controller;

import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.RequestBody;

import java.io.IOException;

public class LoginController extends AbstractController {

    private final UserProcessor userProcessor;

    public LoginController(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        RequestBody body = httpRequest.getRequestBody();
        boolean validUser = userProcessor.isValidUser(body.get("userId"), body.get("password"));

        if (validUser) {
            httpResponse.responseCookies("/index.html", true);
            return;
        }

        httpResponse.responseCookies("/user/login_failed.html", false);
    }
}
