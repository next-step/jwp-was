package webserver.servlet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListServlet implements Servlet {

    @Override
    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) {
        String cookie = httpRequest.getHeader("Cookie");
        validate(cookie);

        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        String[] cookies = cookie.split(";");
        Arrays.stream(cookies)
            .filter(s -> s.contains("logined"))
            .map(s -> s.split("="))
            .map(piece -> piece[1].trim())
            .findFirst()
            .ifPresentOrElse(
                s -> handleLoginUser(httpResponse),
                () -> handleNotLoginUser(httpResponse)
            );
    }

    private void validate(String cookie) {
        Objects.requireNonNull(cookie);
    }

    private void handleLoginUser(final HttpResponse httpResponse) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/list");
            String profilePage = template.apply(Collections.singletonMap("users", DataBase.findAll()));
            byte[] body = profilePage.getBytes(StandardCharsets.UTF_8);

            httpResponse.ok();
            httpResponse.addHeader("Content-Length", Integer.toString(body.length));
            httpResponse.setBody(body);
        } catch (IOException e) {
            throw new IllegalStateException("Handlebars template error");
        }
    }

    private void handleNotLoginUser(HttpResponse httpResponse) {
        httpResponse.found();
        httpResponse.addHeader("Location", "http://localhost:8080/user/login.html");
    }

}
