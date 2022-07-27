package webserver.controller;

import org.springframework.http.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class NotFoundController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.makeStatus(HttpStatus.NOT_FOUND);
        response.addBody(new byte[0]);
        try {
            response.forward(response, "/error/not_found.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
