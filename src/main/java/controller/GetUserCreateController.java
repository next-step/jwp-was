package controller;

import service.CreateUserService;
import webserver.request.Request;
import webserver.response.Response;

public class GetUserCreateController implements Controller {
    @Override
    public Response service(Request request) {
        return CreateUserService.doGet(request);
    }
}
