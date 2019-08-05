package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.HttpParameter;
import webserver.ModelAndView;
import webserver.StatusCode;
import webserver.request.RequestHolder;
import webserver.response.ResponseHolder;

import java.util.Collections;
import java.util.HashMap;

public class LoginServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public String getName() {
        return "/user/login";
    }

    @Override
    public ModelAndView service(RequestHolder requestHolder, ResponseHolder responseHolder) {
        logger.debug("login process.");
        UserService userService = UserService.getInstance();


        HttpParameter httpParameter = requestHolder.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");

        User user = userService.get(userId);

        logger.debug(user + " " + userId + "/" + password);

        if (user != null && user.getPassword().equals(password)) {
            return new ModelAndView.Builder("/index.html")
                    .addCookie("logined", "true")
                    .redirect(true)
                    .build();
        }

        return new ModelAndView.Builder("/user/login_failed.html")
                .addCookie("logined", "false")
                .redirect(true)
                .build();

    }
}
