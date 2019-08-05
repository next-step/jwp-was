package webserver.servlet;

import service.UserService;
import webserver.ModelAndView;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import webserver.response.ResponseHolder;

public class UserListServlet implements Servlet {

    @Override
    public String getName() {
        return "/user/list";
    }

    @Override
    public ModelAndView service(RequestHolder requestHolder, ResponseHolder responseHolder) {
        UserService userService = UserService.getInstance();
        RequestHeader requestHeader = requestHolder.getRequestHeader();
        String logined = requestHeader.getCookie("logined");

        if (Boolean.parseBoolean(logined)) {
            return new ModelAndView.Builder("/user/list.html")
                    .addAttributes("users", userService.getAll())
                    .build();
        }

        return new ModelAndView.Builder("/user/login.html")
                .build();
    }
}
