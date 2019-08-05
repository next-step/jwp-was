package webserver.servlet;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.request.RequestHeader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RegistrationServlet implements Servlet{

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    @Override
    public String getName() {
        return "/user/create";
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("{} service process, registration user ", getName());

        UserService userService = UserService.getInstance();
        HttpParameter httpParameter = httpRequest.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");
        String name = httpParameter.getParameter("name");
        String email = httpParameter.getParameter("email");

        userService.add(new User(userId, password, name, email));

        httpResponse.sendRedirect("/index.html");
    }
}
