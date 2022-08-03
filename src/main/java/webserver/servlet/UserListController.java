package webserver.servlet;

import db.DataBase;
import java.io.IOException;
import java.util.Collections;
import utils.HandlebarsTemplate;
import webserver.domain.ContentType;
import webserver.domain.HttpHeader;
import webserver.domain.HttpSession;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListController implements Controller {

    public static final String KEY_LOGINED = "logined";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession session = httpRequest.getSession();

        if (!session.isLogined()) {
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
        httpResponse.sendRedirect("/user/login.html");
    }

}
