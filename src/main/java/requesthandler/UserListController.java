package requesthandler;

import db.DataBase;
import utils.HandlebarsLoader;
import webserver.controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Collections;

/**
 * Created by hspark on 2019-08-05.
 */
public class UserListController extends AbstractController {
    public static final String LOGIN_COOKIE_NAME = "logined";
    public static final String URL = "/user/list";
    public static final String LOGIN_VALUE = "true";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String logined = httpRequest.getCookie(LOGIN_COOKIE_NAME);
        if (!isLogined(logined)) {
            httpResponse.redirect("/index.html");
            return;
        }

        HandlebarsLoader handlebarsLoader = new HandlebarsLoader();
        String template = handlebarsLoader.load("/user/list", Collections.singletonMap("users", DataBase.findAll()));
        httpResponse.ok(template.getBytes());
    }

    private boolean isLogined(String logined) {
        return Boolean.parseBoolean(logined);
    }
}
