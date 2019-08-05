package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Parameter;
import webserver.response.ResponseHolder;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import service.UserService;

public class RegistrationServlet implements Servlet{

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    @Override
    public String getName() {
        return "/user/create";
    }

    @Override
    public void service(RequestHolder requestHolder, ResponseHolder responseHolder) {
        logger.debug("{} service process, registration user ", getName());

        UserService userService = UserService.getInstance();
        Parameter parameter = requestHolder.getMergedParameter();
        String userId = parameter.getParameter("userId");
        String password = parameter.getParameter("password");
        String name = parameter.getParameter("name");
        String email = parameter.getParameter("email");

        userService.add(new User(userId, password, name, email));

        responseHolder.setRedirect(true);
        responseHolder.setViewName("/index.html");
    }
}
