package webserver.controller;

import webserver.request.HttpRequest;
import webserver.request.Model;
import webserver.response.HttpResponse;
import webserver.service.RequestService;

import java.io.IOException;

public class ListUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Model model = new Model();
        RequestService requestService = new RequestService(request);

        if (!request.getCookie(LOGINED_KEY)) {
            response.forward("user/list");
            return ;
        }
        model.set("users", requestService.findAllUser());
        response.addModel(model);
        try {
            response.forward(response, "user/list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
