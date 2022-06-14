package service;

import db.DataBase;
import model.User;
import webserver.request.RequestLine;
import webserver.response.Response;

import java.util.Map;

public class UserCreateService extends GetService {

    public Response doGet(RequestLine requestLine) {
        Map<String, String> queryMap = requestLine.getUri().getQuery().getQueryMap(); // TODO 추상화
        User user = User.from(queryMap);
        DataBase.addUser(user);
        return Response.response302("/index.html");
    }

    @Override
    public boolean canServe(RequestLine requestLine) {
        return requestLine.matchPath("/user/create");
    }
}
