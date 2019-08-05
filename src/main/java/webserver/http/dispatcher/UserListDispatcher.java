package webserver.http.dispatcher;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListDispatcher<V extends ViewResolver> extends AbstractDispatcher<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(UserListDispatcher.class);

    private V viewResolver;

    public UserListDispatcher(V viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        Boolean logined = Boolean.valueOf(request.getCookie("logined"));

        try {
            if (logined) {
                Map<String, Object> data = new HashMap<>();
                Collection<User> users = DataBase.findAll();
                data.put("users", users);

                View view = viewResolver.render("user/list", data);

                response.writeHeader(request.getAccept(), view.getLength());
                response.writeBody(view.getBody());
            } else {
                response.redirect("/user/login.html", false);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][HANDLEBAR] failed. {}", e);
        }
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetRequest()) {
            doGet(request, response);
        }

        if (request.isPostRequest()) {
            doPost(request, response);
        }
    }
}
