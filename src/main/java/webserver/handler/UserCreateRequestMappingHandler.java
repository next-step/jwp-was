package webserver.handler;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class UserCreateRequestMappingHandler extends RequestMappingHandler {

    public UserCreateRequestMappingHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());
        headers.put("Location", httpRequest.getPath());
        byte[] body = viewResolver.resolve("/user/form.html");
        return HttpResponse.ok(headers, body);
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        Map<String, String> requestBody = httpRequest.getBody();
        User newUser = new User(
                requestBody.get("userId"),
                requestBody.get("password"),
                requestBody.get("name"),
                requestBody.get("email")
        );
        DataBase.addUser(newUser);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());
        headers.put("Location", "/");
        byte[] body = viewResolver.resolve("/index.html");
        return HttpResponse.redirect(headers, body);
    }

    @Override
    protected String getRequestMapping() {
        return "/user/create";
    }
}