package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static webserver.http.HttpDispatcher.dispatcher;
import static webserver.provider.ConfigurationProvider.LOGINED_KEY;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("login process.");
        UserService userService = UserService.getInstance();

        HttpParameter httpParameter = httpRequest.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");

        User user = userService.get(userId);

        logger.debug(user + " " + userId + "/" + password);

        if (user != null && user.getPassword().equals(password)) {
            httpResponse.addCookie(LOGINED_KEY, "true");
            dispatcher(httpRequest, httpResponse).redirect("/index.html");
        }

        httpResponse.addCookie(LOGINED_KEY, "false");
        dispatcher(httpRequest, httpResponse).redirect("/user/login_failed.html");
    }

}
