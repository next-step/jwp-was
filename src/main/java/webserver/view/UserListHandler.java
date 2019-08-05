package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractRequestMappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.HandleBarTemplateLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpHeaders.COOKIE;

public class UserListHandler extends AbstractRequestMappingHandler {

    public static final String LOGIN_TRUE_COOKIE = "logined=true";

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
            if (LOGIN_TRUE_COOKIE.equals(request.getHeader(COOKIE))) {
                Map<String, Object> data = findAllUsers();
                byte[] body = HandleBarTemplateLoader.loadTemplate("/user/list", data);

                response.response200Header(body.length, "text/html;");
                response.responseBody(body);

            } else {
                response.response302Header("/user/login.html", false);
            }
    }

    private Map<String, Object> findAllUsers() {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        return data;
    }
}
