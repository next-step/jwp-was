package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionManagement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static utils.HandlebarsUtils.loader;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private static final String USER_LIST_PATH = "/user/list.html";
    public static final String ROOT_FILE = "/index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        logger.debug("UserListController : {}", httpRequest.getRequestPath());

        if (!isLogin(httpRequest)) {
            return HttpResponse.sendRedirect(ROOT_FILE);
        }
        Collection<User> users = DataBase.findAll();

        return HttpResponse.forward(USER_LIST_PATH, loader(users).getBytes(StandardCharsets.UTF_8));
    }

    private boolean isLogin(HttpRequest httpRequest) {
        HttpSession httpSession = SessionManagement.getSession(httpRequest.getSessionId());
        String isLogin = String.valueOf(httpSession.getAttribute("logined"));
        
        if (isLogin == null || isLogin.equals("false")) {
            return false;
        }
        return isLogin.equals("true");
    }
}
