package webserver.controller;

import service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ViewResolver;
import webserver.http.request.RequestHeader;

import static webserver.WebContext.LOGINED_KEY;

public class ListUserController extends AbstractController {

    private final UserService userService;

    public ListUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        String logined = requestHeader.getCookie(LOGINED_KEY);

        if (Boolean.parseBoolean(logined)) {
            httpResponse.addAttributes("users", userService.getAll());
            ViewResolver.from(httpRequest, httpResponse).forward("/user/list.html");
        }

        ViewResolver.from(httpRequest, httpResponse).redirect("/user/login.html");
    }

}
