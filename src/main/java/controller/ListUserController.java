package controller;

import http.TemplateLoader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class ListUserController extends AbstractController {

    @Override
    public String getPath() {
        return "/list/user";
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String cookie = request.getHeader("Cookie");
        if (cookie.contains("logined=true")) {
            User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
            TemplateLoader<User> loader = new TemplateLoader("user/list");

            String profilePage = loader.applyTemplate(user);

            byte[] body = profilePage.getBytes();
            response.response200Header(body.length);
            response.responseBody(body);
        }
        response.sendRedirect("http://localhost:8080/index.html");
    }
}
