package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private static final String LOGINED = "logined";

    @Override
    public String getName() {
        return "/user/login";
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("login process.");
        UserService userService = UserService.getInstance();

        HttpParameter httpParameter = httpRequest.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");

        User user = userService.get(userId);

        logger.debug(user + " " + userId + "/" + password);

        if (user != null && user.getPassword().equals(password)) {
            httpResponse.addCookie(LOGINED, "true");
            httpResponse.sendRedirect("/index.html");
        }

        httpResponse.addCookie(LOGINED, "false");
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
