package controller;

import service.FailedLoginException;
import service.UserLoginService;
import webserver.request.Body;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseFactory;

public class UserLoginController extends PostController {

    private static final UserLoginService userLoginService = new UserLoginService();

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Body body = httpRequest.getBody();
        String userId = body.get("userId");
        String password = body.get("password");

        try {
            String sessionId = userLoginService.login(userId, password);
            HttpResponse httpResponse = HttpResponseFactory.response302("/index.html");
            httpResponse.putCookie("sessionId", sessionId);
            return httpResponse;
        } catch (FailedLoginException e) {
            return HttpResponseFactory.response302("/user/login_failed.html");
        }
    }
}
