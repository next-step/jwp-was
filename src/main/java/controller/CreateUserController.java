package controller;

import service.CreateUserService;
import webserver.request.Request;
import webserver.response.Response;

public class CreateUserController implements Controller {
    @Override
    public Response service(Request request) {
        return CreateUserService.createUser(request);
    }
}
