package http.controller;

import com.sun.istack.internal.logging.Logger;
import db.DataBase;
import http.HttpRequest;
import http.QueryString;
import model.User;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class UserController extends PathController {

    private static final Logger log = Logger.getLogger(UserController.class);

    public UserController(HttpRequest httpRequest) {
        super(httpRequest);
    }

    public byte[] post() {
        log.info("user controller post method ===========");
        QueryString requestBodyQuery = new QueryString(httpRequest.getRequestBody());

        User user = new User(requestBodyQuery.getParameter("userId"),
                requestBodyQuery.getParameter("password"),
                requestBodyQuery.getParameter("name"),
                requestBodyQuery.getParameter("email"));

        DataBase.addUser(user);
        return new byte[0];
    }
}
