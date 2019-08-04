package webserver.servlet;

import service.UserService;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import webserver.response.ResponseHolder;

public class UserListServlet implements Servlet {

    @Override
    public String getName() {
        return "/user/list";
    }

    @Override
    public void service(RequestHolder requestHolder, ResponseHolder responseHolder) {
        UserService userService = UserService.getInstance();
        RequestHeader requestHeader = requestHolder.getRequestHeader();
        String logined = requestHeader.getCookie("logined");

        if (!"true".equals(logined)) {
            responseHolder.setViewName("/user/login.html");
            return;
        }

        requestHolder.addAttributes("users", userService.getAll());
        responseHolder.setViewName("/user/list.html");
    }
}
