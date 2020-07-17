package controller.login;

import controller.AbstractController;
import db.DataBase;
import http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));

        if (user == null) {
            response.addHeader("Location", "/user/login_failed.html");
            response.sendRedirect(HttpResponseCode.REDIRECT_300);
            return;
        }

        if (user.getPassword().equals(request.getParameter("password"))) {

            HttpSession session = HttpSessionManager.sessionCreate();

            session.setAttribute("user", user);

            Cookie cookie = new Cookie();
            cookie.put("JSESSIONID", session.getId());
            cookie.setPath("/");

            response.addCookie(cookie);
            response.addHeader("Location", "/index.html");
            response.sendRedirect(HttpResponseCode.REDIRECT_300);
        } else {
            response.forward("/user/login_failed.html");
        }
    }
}
