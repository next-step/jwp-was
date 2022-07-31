package webserver.servlet;

import static exception.ExceptionStrings.NOT_FOUND_MEMBER;

import db.DataBase;
import model.User;
import utils.NullChecker;
import webserver.domain.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserLoginServlet implements Servlet {

    @Override
    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        validate(userId, password);

        User userFound = DataBase.findUserById(userId)
            .orElseThrow(() -> new IllegalStateException(NOT_FOUND_MEMBER));

        if (userFound.fitPassword(password)) {
            httpResponse.addHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");
            httpResponse.sendRedirect("http://localhost:8080/index.html");
            return;
        }

        httpResponse.addHeader(HttpHeader.SET_COOKIE, "logined=false; Path=/");
        httpResponse.sendRedirect("http://localhost:8080/user/login_failed.html");
    }

    private void validate(String userId, String password) {
        NullChecker.requireNonNull(userId, password);
    }

}
