package http.controller;

import com.sun.istack.internal.logging.Logger;
import db.DataBase;
import http.HttpHeaderInfo;
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

        HttpHeaderInfo headerInfo = new HttpHeaderInfo();
        headerInfo.addKeyAndValue("Location", "http://localhost:8080/index.html");
        HttpResponse httpResponse = new HttpResponse(HttpResponseCode.REDIRECT,new byte[0],headerInfo);
        return httpResponse.makeResponseBody();
    }
}
