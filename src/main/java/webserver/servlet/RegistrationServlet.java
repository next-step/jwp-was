package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.URLQuery;
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
    public void service(RequestHolder requestHolder) {
        logger.debug("{} service process, registration user ", getName());

        UserService userService = UserService.getInstance();
        URLQuery urlQuery = requestHolder.getUrlQuery();
        String userId = urlQuery.getParameter("userId");
        String password = urlQuery.getParameter("password");
        String name = urlQuery.getParameter("name");
        String email = urlQuery.getParameter("email");

        userService.add(new User(userId, password, name, email));
    }
}
