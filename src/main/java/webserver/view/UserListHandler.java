package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractRequestMappingHandler;
import webserver.ResourceLoader;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.HandleBarTemplateLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpHeaders.*;

public class UserListHandler extends AbstractRequestMappingHandler {

    public static final String LOGIN_TRUE_COOKIE = "logined=true";

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
            if (LOGIN_TRUE_COOKIE.equals(request.getHeader(COOKIE))) {
                Map<String, Object> data = findAllUsers();
                byte[] body = HandleBarTemplateLoader.loadTemplate("/user/list", data);

                response.addHeader(CONTENT_LENGTH, body.length);
                response.addHeader(CONTENT_TYPE, ResourceLoader.resourceContentType("text/html;"));
                response.response200Header();
                response.responseBody(body);

            } else {
                response.addHeader(LOCATION, "/user/login.html");
                response.addHeader(SET_COOKIE, "logined=false; Path=/");
                response.response302Header();
            }
    }

    private Map<String, Object> findAllUsers() {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        return data;
    }
}
