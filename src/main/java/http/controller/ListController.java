package http.controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.enums.ContentType;
import model.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HandlebarLoadUtils;

import java.io.IOException;
import java.util.ArrayList;

public class ListController extends PathController {
    private static final Logger log = LoggerFactory.getLogger(ListController.class);


    public void doGet(HttpRequest request, HttpResponse response) {
        log.info("list controller get method execute ========");
        response.addHeader("Content-Type", ContentType.HTML.getMimeType());

        if (!request.isLoggedIn()) {
            response.sendRedirect("/index.html");
            return;
        }
        try {
            UserList userList = new UserList(new ArrayList<>(DataBase.findAll()));

            String profilePage = HandlebarLoadUtils.getHandlebarTemplate("user/profile")
                    .apply(userList);

            response.forwordHandleBar(profilePage);
        } catch (IOException e) {
            log.error("List IO Exception : {}", e);
        }
    }
}
