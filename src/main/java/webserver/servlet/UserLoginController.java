package webserver.servlet;

import static exception.ExceptionStrings.NOT_FOUND_MEMBER;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.Maps;
import db.DataBase;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import model.User;
import utils.NullChecker;
import webserver.domain.Cookie;
import webserver.domain.HttpSession;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserLoginController implements Controller {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        validate(userId, password);

        User userFound = DataBase.findUserById(userId)
            .orElseThrow(() -> new IllegalStateException(NOT_FOUND_MEMBER));

        if (!userFound.fitPassword(password)) {
            httpResponse.addCookie(Cookie.notLoginedWithPath("/"));
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }

        HttpSession session = httpRequest.getSession();
        httpResponse.addCookie(session.getLoginedCookie());
        httpResponse.sendRedirect("/index.html");
    }

    private void validate(String userId, String password) {
        NullChecker.requireNonNull(userId, password);
    }

}
