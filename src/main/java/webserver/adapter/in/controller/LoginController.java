package webserver.adapter.in.controller;

import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.RequestBody;

public class LoginController extends AbstractController {

    private final UserProcessor userProcessor;

    public LoginController(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody body = httpRequest.getRequestBody();
        boolean validUser = userProcessor.isValidUser(body.get("userId"), body.get("password"));

        if (validUser) {
            httpResponse.responseRedirect("/index.html", true, true);
            return;
        }

        httpResponse.responseRedirect("/user/login_failed.html", true, false);
    }
}
