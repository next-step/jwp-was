package http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserController extends DefaultController {

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        super.handle(request, response);
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        if (request.getPath().equals("/user/list")) {
            list(request, response);
            return;
        }
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        if (request.getPath().equals("/user/create")) {
            create(request, response);
            return;
        }

        if (request.getPath().equals("/user/login")) {
            login(request, response);
            return;
        }

    }

    private void list(final HttpRequest request, final HttpResponse response) {
        String cookie = request.getCookie();
        final String[] cookieTokens = cookie.split(";");
        if (cookieTokens.length > 1 && cookieTokens[1].trim().equals("logined=true")) {

            response.buildResponseLine(HttpStatus.OK);
            response.setCharset("utf-8");
            response.setResponseBody("user/list.html");

            try {
                Template template = getHandlebars()
                        .compile("user/list");
                String apply = template.apply(DataBase.findAll());
                response.setTemplate(apply);
                response.print();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.buildResponseLine(HttpStatus.FOUND);
            response.setCharset("utf-8");
            response.setResponseBody("/user/login.html");
            response.print();
        }
    }

    private Handlebars getHandlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        loader.setCharset(StandardCharsets.UTF_8);
        return new Handlebars(loader);
    }

    private void login(final HttpRequest request, final HttpResponse response) {
        final Map<String, String> requestBody = request.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User userById = DataBase.findUserById(userId);
        if (userById.getPassword().equals(password)) {
            response.buildResponseLine(HttpStatus.FOUND);
            response.setContentType("text/html; charset=utf-8");
            response.setCookie(true);
            response.setResponseBody("/index.html");
            response.print();
            return;
        }
        response.buildResponseLine(HttpStatus.FOUND);
        response.setContentType("text/html; charset=utf-8");
        response.setCookie(false);
        response.setResponseBody("/user/login_failed.html");
        response.print();
    }

    private void create(final HttpRequest request, final HttpResponse response) {
        final Map<String, String> requestBody = request.getRequestBody();
        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        final String name = requestBody.get("name");
        final String email = requestBody.get("email");

        final int changeBefore = DataBase.findAll().size();
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        final int changeAfter = DataBase.findAll().size();

        if (changeAfter - 1 == changeBefore) {
            response.buildResponseLine(HttpStatus.FOUND);
            response.setContentType("text/html; charset=utf-8");
            response.setResponseBody("/index.html");
            response.print();
            return;
        }
    }

}
