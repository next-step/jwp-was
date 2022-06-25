package webserver.adapter.in.controller;

import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;
import webserver.application.UserProcessor;
import webserver.domain.http.RequestBody;

public class CreateUserController extends AbstractController {

    private final UserProcessor userProcessor;

    public CreateUserController(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody body = httpRequest.getRequestBody();
        userProcessor.createUser(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));

        httpResponse.responseRedirect("/index.html", false, false);
    }
}
