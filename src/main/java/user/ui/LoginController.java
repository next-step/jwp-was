package user.ui;

import controller.BaseController;
import db.DataBase;
import http.ResponseObject;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginController extends BaseController {

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
                httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                httpResponse.sendRedirect("/index.html");
            }
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");

        } catch (NullPointerException e) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
