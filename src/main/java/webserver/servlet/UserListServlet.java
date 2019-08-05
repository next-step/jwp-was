package webserver.servlet;

import service.UserService;
import utils.StringUtils;
import webserver.http.HttpRequest;
import webserver.http.request.RequestHeader;
import webserver.http.HttpResponse;

public class UserListServlet implements Servlet {

    @Override
    public String getName() {
        return "/user/list";
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService userService = UserService.getInstance();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        String logined = requestHeader.getCookie("logined");

        if (StringUtils.isNotBlank(logined) && Boolean.parseBoolean(logined)) {
            httpRequest.addAttributes("users", userService.getAll());
            httpResponse.forward(httpRequest, "/user/list.html");
        }

        httpResponse.sendRedirect("/user/login.html");
    }
}
