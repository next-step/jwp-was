package webserver.controller;

import db.DataBase;
import model.User;
import utils.IOUtils;
import webserver.ContentType;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.session.HttpSession;
import webserver.session.HttpSessionRepository;
import webserver.util.HandlebarsObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class UserListController extends GetController {

    private static final String DELIMITER1 = " ";
    private static final String DELIMITER2 = "=";

    @Override
    public HttpResponse doGet(HttpRequest request) {
        Map<String, String> cookiesByName = parseCookies(request);

        try {
            String path = "";
            if (isNotLogin(cookiesByName)) {
                path = IOUtils.loadFileFromClasspath("./templates/user/login.html");
                return HttpResponse.forward(path, ContentType.from(path).getMediaType());
            }

            HandlebarsObject handlebarObj = HandlebarsObject.createHandlebarObject("/templates", ".html");
            Collection<User> users = DataBase.findAll();
            path = handlebarObj.applyTemplate("user/list", users);
            return HttpResponse.forward(path, ContentType.from(path).getMediaType());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return HttpResponse.notFound("/index.html");
    }

    private boolean isNotLogin(Map<String, String> cookiesMap) {
        String sessionId = cookiesMap.get("sessionId");

        HttpSession httpSession = HttpSessionRepository.getSession(sessionId);
        Object logined = httpSession.getAttribute("logined");

        if(logined == null) {
            return true;
        }

        return logined.equals("false");
    }

    private Map<String, String> parseCookies(HttpRequest request) {
        String headerValue = request.getHeader("Cookie");

        String[] cookies = headerValue.split(DELIMITER1);

        Map<String, String> map = Arrays.stream(cookies).
                map(cookie -> cookie.split(DELIMITER2)).
                collect(Collectors.toMap(datas -> datas[0], datas -> datas[1], (a, b) -> b));
        return map;
    }
}
