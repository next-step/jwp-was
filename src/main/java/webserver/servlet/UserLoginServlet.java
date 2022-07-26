package webserver.servlet;

import static exception.ExceptionStrings.NOT_FOUND_MEMBER;

import db.DataBase;
import java.util.Map;
import model.User;
import utils.NullChecker;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserLoginServlet implements Servlet {

    @Override
    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> bodyQueryStrings = httpRequest.bodyQueryString();
        String userId = bodyQueryStrings.get("userId");
        String password = bodyQueryStrings.get("password");
        validate(userId, password);

        User userFound = DataBase.findUserById(userId)
            .orElseThrow(() -> new IllegalStateException(NOT_FOUND_MEMBER));

        httpResponse.protocol1_1();
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        if (userFound.fitPassword(password)) {
            httpResponse.statusOk();
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
            return;
        }

        httpResponse.statusFound();
        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        httpResponse.addHeader("Location", "http://localhost:8080/user/login_failed.html");
    }

    private void validate(String userId, String password) {
        NullChecker.requireNonNull(userId, password);
    }

}
