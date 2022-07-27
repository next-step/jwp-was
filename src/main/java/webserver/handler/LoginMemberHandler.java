package webserver.handler;

import db.DataBase;
import model.User;
import org.checkerframework.checker.units.qual.C;
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
    public Response handle(Request request) {
        Map<String, String> parseBody = urlEncodedBodyParser.parseBody(request.getRequestBody());
        String userId = parseBody.get("userId");
        String password = parseBody.get("password");
        User userById = DataBase.findUserById(userId);

        if (userById != null && userById.matchPassword(password)) {
            return createResponse(new Cookie("logined", "true", "/"), "/index.html");
        }

        return createResponse(new Cookie("logined", "false", "/"), "/user/login_failed.html");
    }

    private Response createResponse(Cookie cookie, String location) {
        Response response = new Response();
        response.addCookie(cookie);
        response.sendRedirect(location);
        return response;
    }

}
