package webserver.servlet;

import db.DataBase;
import java.io.IOException;
import java.util.Collections;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import utils.HandlebarsTemplate;
import webserver.domain.ContentType;
import webserver.domain.Cookies;
import webserver.domain.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListController implements Controller {

    private static final String COOKIE_NAME_LOGINED = "logined";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
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
            httpResponse.addHeader(HttpHeader.CONTENT_TYPE, ContentType.HTML.type());
            httpResponse.forwardBody(page);
        } catch (IOException e) {
            throw new IllegalStateException("Handlebars template error");
        }
    }

    private void handleNotLoginUser(HttpResponse httpResponse) {
        httpResponse.sendRedirect("http://localhost:8080/user/login.html");
    }

}
