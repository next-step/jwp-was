package http.controller;

import com.sun.istack.internal.logging.Logger;
import db.DataBase;
import http.Header;
import http.HttpRequest;
import http.HttpResponse;
import http.QueryString;
import http.enums.HttpResponseCode;
import model.User;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class UserController extends PathController {

    private static final Logger log = Logger.getLogger(UserController.class);

    public void doPost(HttpRequest request, HttpResponse response) {
        log.info("user controller post method ===========");
        QueryString requestBodyQuery = new QueryString(request.getRequestBody());

        User user = new User(requestBodyQuery.getParameter("userId"),
                requestBodyQuery.getParameter("password"),
                requestBodyQuery.getParameter("name"),
                requestBodyQuery.getParameter("email"));

        DataBase.addUser(user);

        response.sendRedirect("/index.html");
    }
}
