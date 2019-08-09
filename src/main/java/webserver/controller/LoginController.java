package webserver.controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ViewResolver;

import static webserver.WebContext.LOGINED_KEY;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

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
            ViewResolver.from(httpRequest, httpResponse).redirect("/index.html");
            return;
        }

        logger.debug("## login failed. " + userId);
        httpResponse.addCookie(LOGINED_KEY, "false");
        ViewResolver.from(httpRequest, httpResponse).redirect("/user/login_failed.html");
    }

}
