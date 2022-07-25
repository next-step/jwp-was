package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.Cookie;
import webserver.request.Model;
import webserver.request.Request;
import webserver.response.Response;
import webserver.service.RequestService;

public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private static final String REDIRECT = "redirect:";
    private static final String INDEX_URl = "index.html";
    private static final String LOGIN_FAIL_URl = "/user/login_failed.html";

    public static String list(Request request, Response response) {
        Model model = new Model();
        RequestService requestService = new RequestService(request);

        if (!request.getCookie(Cookie.LOGINED_KEY)) {
            return REDIRECT + INDEX_URl;
        }
        model.set("users", requestService.findAllUser());
        response.addModel(model);
        return "user/list";
    }

    public static String create(Request request, Response response) {
        RequestService requestService = new RequestService(request);
        requestService.saveMember();
        return REDIRECT + INDEX_URl;
    }

    public static String login(Request request, Response response) {
        RequestService requestService = new RequestService(request);
        Cookie cookie = requestService.checkIdAndPassword(request.convertUserOfQueryParam());
        response.setCookie(cookie);
        if (!Boolean.parseBoolean(cookie.getCookie(Cookie.LOGINED_KEY))) {
            return REDIRECT + LOGIN_FAIL_URl;
        }
        return REDIRECT + INDEX_URl;
    }
}
