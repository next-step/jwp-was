package webserver.servlet;

import com.google.common.base.Strings;
import db.DataBase;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import utils.HandlebarsTemplate;
import webserver.domain.ContentType;
import webserver.domain.HttpHeader;
import webserver.domain.Sessions;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserListController implements Controller {

    public static final String KEY_LOGINED = "logined";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String sessionId = httpRequest.getSessionId();
        HttpSession session = Sessions.INSTANCE.get(sessionId);

        String sessionLogined = (String) session.getAttribute(KEY_LOGINED);

        if (Strings.isNullOrEmpty(sessionLogined) || !Boolean.parseBoolean(sessionLogined)) {
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
