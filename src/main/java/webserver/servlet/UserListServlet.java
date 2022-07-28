package webserver.servlet;

import db.DataBase;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import utils.HandlebarsTemplate;
import webserver.domain.ContentType;
import webserver.domain.Cookies;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListServlet implements Servlet {

    private static final String COOKIE_NAME_LOGINED = "logined";

    @Override
    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Cookies cookies = httpRequest.getCookies();
        String loginCookie = cookies.get(COOKIE_NAME_LOGINED);

        if (loginCookie.isEmpty() || !Boolean.parseBoolean(loginCookie)) {
            handleNotLoginUser(httpResponse);
            return;
        }

        handleLoginUser(httpResponse);
    }

    private void handleLoginUser(final HttpResponse httpResponse) {
        try {
            String page = HandlebarsTemplate.create("/templates", ".html", "user/list")
                .apply(Collections.singletonMap("users", DataBase.findAll()));
            byte[] body = page.getBytes(StandardCharsets.UTF_8);
            httpResponse.okWithBody(body, ContentType.HTML.type());
        } catch (IOException e) {
            throw new IllegalStateException("Handlebars template error");
        }
    }

    private void handleNotLoginUser(HttpResponse httpResponse) {
        httpResponse.found("http://localhost:8080/user/login.html");
    }

}
