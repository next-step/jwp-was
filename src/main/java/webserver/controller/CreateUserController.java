package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.service.UserService;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        UserService requestService = new UserService(request);
        requestService.saveMember();
        response.makeLocationPath(INDEX_URl);
        response.sendRedirect(request, response);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
