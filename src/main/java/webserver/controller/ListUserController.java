package webserver.controller;

import service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.RequestHeader;

import static webserver.Context.LOGINED_KEY;
import static webserver.http.ViewResolver.from;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService userService = UserService.getInstance();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        String logined = requestHeader.getCookie(LOGINED_KEY);

        if (Boolean.parseBoolean(logined)) {
            httpResponse.addAttributes("users", userService.getAll());
            from(httpRequest, httpResponse).forward("/user/list.html");
        }

        from(httpRequest, httpResponse).redirect("/user/login.html");
    }

}
