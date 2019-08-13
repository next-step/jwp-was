package requesthandler;

import db.DataBase;
import utils.HandlebarsLoader;
import webserver.controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.request.HttpSession;
import webserver.response.HttpResponse;

import java.util.Collections;
import java.util.Objects;

/**
 * Created by hspark on 2019-08-05.
 */
public class UserListController extends AbstractController {
    public static final String LOGIN_SESSION_NAME = "logined";
    public static final String URL = "/user/list";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        if (!isLogined(httpRequest.getHttpSession())) {
            httpResponse.redirect("/index.html");
            return;
        }

        HandlebarsLoader handlebarsLoader = new HandlebarsLoader();
        String template = handlebarsLoader.load("/user/list", Collections.singletonMap("users", DataBase.findAll()));
        httpResponse.ok(template.getBytes());
    }

    private boolean isLogined(HttpSession httpSession) {
        Boolean isLogined = (Boolean) httpSession.getAttribute(LOGIN_SESSION_NAME);
        return Objects.nonNull(isLogined) && isLogined;
    }
}
