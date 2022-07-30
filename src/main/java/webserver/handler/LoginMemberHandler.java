package webserver.handler;

import db.DataBase;
import model.User;
import webserver.Handler;
import webserver.http.*;

import java.util.Map;

public class LoginMemberHandler implements Handler {

    private final UrlEncodedBodyParser urlEncodedBodyParser = new UrlEncodedBodyParser();

    @Override
    public boolean isSupport(Request request) {
        return request.getPath().equals("/user/login") && request.getMethod().isPost();
    }
    
    @Override
    public void handle(Request request, Response response) {
        Map<String, String> parseBody = urlEncodedBodyParser.parseBody(request.getRequestBody());
        String userId = parseBody.get("userId");
        String password = parseBody.get("password");
        User userById = DataBase.findUserById(userId);

        if (userById != null && userById.matchPassword(password)) {
            handleResponse(new Cookie("logined", "true", "/"), "/index.html", response);
        }

        handleResponse(new Cookie("logined", "false", "/"), "/user/login_failed.html", response);
    }

    private void handleResponse(Cookie cookie, String location, Response response) {
        response.addCookie(cookie);
        response.sendRedirect(location);
    }

}
