package webserver.controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static webserver.Context.LOGINED_KEY;
import static webserver.http.ViewResolver.from;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("login process.");

        HttpParameter httpParameter = httpRequest.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");

        User user = userService.get(userId);

        if (user != null && user.getPassword().equals(password)) {
            logger.debug("## login success. " + userId);
            httpResponse.addCookie(LOGINED_KEY, "true");
            from(httpRequest, httpResponse).redirect("/index.html");
            return;
        }

        logger.debug("## login failed. " + userId);
        httpResponse.addCookie(LOGINED_KEY, "false");
        from(httpRequest, httpResponse).redirect("/user/login_failed.html");
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
