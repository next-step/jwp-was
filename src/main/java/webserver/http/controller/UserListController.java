package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController<V extends ViewResolver> extends AbstractController<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private V viewResolver;

    public UserListController(V viewResolver) {
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

                response.writeHeader(HttpStatus.OK, request.getAccept(), view.getLength());
                response.writeBody(view.getBody());
            } else {
                response.addCookie(new Cookie("logined", "false"));
                response.redirect("/user/login");
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][USER LIST] failed. {}", e);
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetRequest()) {
            doGet(request, response);
        }

        if (request.isPostRequest()) {
            try {
                doPost(request, response);
            } catch (IOException e) {
                logger.error("[PROCESS][USER LIST] failed. {}", e);
            }
        }
    }
}
