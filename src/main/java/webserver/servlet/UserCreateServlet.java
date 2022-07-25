package webserver.servlet;

import db.DataBase;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserCreateServlet implements Servlet {

    @Override
    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> queryStrings = httpRequest.getQueryStringsMap();
        User user = new User(queryStrings.get("userId"), queryStrings.get("password"), queryStrings.get("name"), queryStrings.get("email"));
        DataBase.addUser(user);

        httpResponse.protocol1_1();
        httpResponse.statusOk();
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
    }

}
