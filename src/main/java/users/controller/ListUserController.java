package users.controller;

import db.DataBase;
import mvc.controller.AbstractController;
import users.model.User;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.HttpSession;

import java.util.Collection;

public class ListUserController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (!isLogined(request.getSession())) {
            response.sendRedirect("/user/login.html");
            return;
        }

        Collection<User> users = DataBase.findAll();
        request.addAttribute("users", users);
        render(request, response, "/user/list");
    }

    private static boolean isLogined(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return false;
        }
        return true;
    }
}
