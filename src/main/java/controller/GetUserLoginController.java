package controller;

import service.LoginService;
import webserver.request.Request;
import webserver.response.Response;

public class GetUserLoginController implements Controller {
    @Override
    public Response service(Request request) {
        return LoginService.doGet(request);
    }
}
