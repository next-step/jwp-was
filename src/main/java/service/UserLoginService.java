package service;

import db.DataBase;
import db.FailedLoginException;
import webserver.request.RequestBody;
import webserver.request.RequestLine;
import webserver.response.Response;

public class UserLoginService extends PostService {

    @Override
    public Response doPost(RequestLine requestLine, RequestBody requestBody) {
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        try {
            DataBase.login(userId, password);
            Response response = Response.response302("/index.html");
            response.putCookie("logined", "true");
            return response;
        } catch (FailedLoginException e) {
            Response response = Response.response302("/user/login_failed.html");
            response.putCookie("logined", "false");
            return response;
        }
    }

    @Override
    public boolean canServe(RequestLine requestLine) {
        return requestLine.matchPath("/user/login");
    }
}
