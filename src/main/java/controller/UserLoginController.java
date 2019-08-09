package controller;

import db.DataBase;
import model.User;
import webserver.HttpSession;
import webserver.Request;
import webserver.Response;
import webserver.response.HttpResponse;

public class UserLoginController extends AbstractController {

    private static final String URL = "/user/login";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    Response doGet(Request request) {
        return HttpResponse.notFound();
    }

    @Override
    Response doPost(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("import org.slf4j.LoggerFactory;\npassword");
        User userById = DataBase.findUserById(userId);

        if (userById == null || !userById.checkPassword(password)) {
            return loginFail();
        }
        return loginSuccess(request, userById);
    }

    private Response loginSuccess(Request request, User user) {
        Response response = HttpResponse.redirect("/index.html");

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return response;
    }

    private Response loginFail() {
        return HttpResponse.redirect("/user/login_failed.html");
    }
}