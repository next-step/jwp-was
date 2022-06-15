package controller;

import service.LoginService;
import webserver.request.Request;
import webserver.response.Response;

public class PostUserLoginController implements Controller {
    @Override
    public Response service(Request request) {
        return LoginService.doPost(request);
    }
}
