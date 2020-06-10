package http.controller;

import db.DataBase;
import http.*;

import http.enums.ContentType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends PathController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public void doGet(HttpRequest request, HttpResponse response) {
        log.info("login Controller get methid =========");

        if (request.isLoggedIn()) {
            response.sendRedirect("/index.html");
            return;
        }
        doGetDefault(request, response);
    }

    public void doPost(HttpRequest request, HttpResponse response) {
        log.info("login controller post method ===========");

        response.addHeader("Content-Type", ContentType.HTML.getMimeType());

        QueryString requestBodyString = new QueryString(request.getRequestBody());
        User loginUser = new User(requestBodyString.getParameter("userId"), requestBodyString.getParameter("password"));
        User findUser = DataBase.findUserById(requestBodyString.getParameter("userId"));

        if (findUser != null && findUser.equals(loginUser)) {
            HttpSession session = request.getSession();
            session.setAttribute("user",findUser);
            response.addCookie("sessionId", session.getId());
            response.sendRedirect("/index.html");
            return;
        }

        response.sendRedirect("/user/login_failed.html");
    }

}
