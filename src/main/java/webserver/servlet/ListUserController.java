package webserver.servlet;

import service.UserService;
import utils.StringUtils;
import webserver.http.HttpRequest;
import webserver.http.request.RequestHeader;
import webserver.http.HttpResponse;
import webserver.http.HttpDispatcher;

import static webserver.http.HttpDispatcher.dispatcher;
import static webserver.provider.ConfigurationProvider.LOGINED_KEY;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService userService = UserService.getInstance();
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        String logined = requestHeader.getCookie(LOGINED_KEY);

        if (StringUtils.isNotBlank(logined) && Boolean.parseBoolean(logined)) {
            httpRequest.addAttributes("users", userService.getAll());
            dispatcher(httpRequest, httpResponse).forward("/user/list.html");
        }

        dispatcher(httpRequest, httpResponse).redirect("/user/login.html");
    }

}
