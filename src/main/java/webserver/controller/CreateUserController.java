package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.service.RequestService;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        RequestService requestService = new RequestService(request);
        requestService.saveMember();
        response.makeLocationPath(INDEX_URl);
        response.sendRedirect(request, response);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
