package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.Parameter;
import webserver.request.RequestHolder;
import webserver.response.ResponseHolder;

public class LoginServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public String getName() {
        return "/user/login";
    }

    @Override
    public void service(RequestHolder requestHolder, ResponseHolder responseHolder) {
        logger.debug("login process.");
        UserService userService = UserService.getInstance();

        Parameter parameter = requestHolder.getMergedParameter();
        String userId = parameter.getParameter("userId");
        String password = parameter.getParameter("password");

        User user = userService.get(userId);

        logger.debug(user + " " + userId + "/" + password);

        if (user != null && user.getPassword().equals(password)) {
            responseHolder.addCookie("logined", "true");
            responseHolder.setViewName("/index.html");
            return;
        }

        responseHolder.addCookie("logined", "false");
        responseHolder.setViewName("/user/login_failed.html");
    }
}
