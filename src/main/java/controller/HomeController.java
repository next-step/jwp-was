package controller;

import annotation.GetMapping;
import model.HttpHeaders;
import model.HttpResponseMessage;
import model.Session;
import model.SessionStorage;
import types.HttpStatus;
import types.MediaType;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class HomeController {

    @GetMapping(path = "/")
    public HttpResponseMessage index() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.TEXT_HTML);
        httpHeaders.setLocation(URI.create("http://localhost:8080/index.html").toString());

        UUID uuid = UUID.randomUUID();
        SessionStorage.getInstance().storeSession(uuid.toString(), new Session());
        httpHeaders.setSessionIdInCookie(uuid.toString());

        return new HttpResponseMessage(HttpStatus.FOUND, httpHeaders);
    }

}
