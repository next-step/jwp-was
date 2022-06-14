package controller;

import java.io.IOException;
import service.ListUserService;
import webserver.request.Request;
import webserver.response.Response;

public class ListUserController implements Controller {
    @Override
    public Response service(Request request) throws IOException {
        return ListUserService.getUserList(request);
    }
}
