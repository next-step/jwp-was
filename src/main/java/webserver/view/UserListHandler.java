package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractHandler;
import webserver.ResourceLoader;
import webserver.http.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static webserver.view.SessionUtil.SESSION_KEY;

public class UserListHandler extends AbstractHandler {

    public UserListHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute(SESSION_KEY);

        if(user == null) {
            response.response302Header("/user/login.html");
        } else {
            Map<String, Object> data = findAllUsers();
            byte[] body = viewResolver.loadView("user/list", data);

            response.response200Header(body.length, ResourceLoader.resourceContentType("text/html;"));
            response.responseBody(body);
        }
    }

    private Map<String, Object> findAllUsers() {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);

        return data;
    }
}
