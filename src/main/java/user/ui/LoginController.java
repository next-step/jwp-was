package user.ui;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import http.session.HttpSessionHandler;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class LoginController extends UserController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        try {
            Map<String, String> requestParameters = httpRequest.getRequestParameters();
            String userId = requestParameters.getOrDefault("userId", "");
            String password = requestParameters.getOrDefault("password", "");
            User user = DataBase.findUserById(userId);
            logger.debug(user.toString());
            if (user.isCorrectPassword(password)) {
                HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
                HttpSessionHandler.applySession(httpSession);
                httpSession.setAttribute("logined", true);
                httpResponse.addSessionId(httpSession.getId());
                httpResponse.sendRedirect("/index.html");
                return;
            }
            httpResponse.sendRedirect("/user/login_failed.html");

        } catch (NullPointerException e) {
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
