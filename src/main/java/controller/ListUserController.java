package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class ListUserController extends AbstractController {

    @Override
    public String getPath() {
        return "/list/user";
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        String cookie = request.getHeader("Cookie");
        if (cookie.contains("logined=true")) {
            try {
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);

                Template template = handlebars.compile("user/list");
                User user2 = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
                String profilePage = template.apply(user2);

                byte[] body = profilePage.getBytes();
                response.response200Header(body.length);
                response.responseBody(body);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        response.sendRedirect("http://localhost:8080/index.html");
    }
}
