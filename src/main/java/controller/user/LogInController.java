package controller.user;

import db.DataBase;
import model.User;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class LogInController {

    public static final String URL = "/user/login";
    public static final String VIEW_PATH = "/user/login.html";
    public static final String successRedirectPath = "/index.html";
    public static final String failRedirectPath = "/user/login_failed.html";

    public static final String LOGIN_SUCCESS_COOKIE = "logined=true; Path=/";
    public static final String LOGIN_FAIL_COOKIE = "logined=false; Path=/";

    public HttpResponse run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet();
        } else if (httpMethod.equals(HttpMethod.POST)) {
            return doPost(httpRequest.getRequestBody());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private HttpResponse doGet() {
        return HttpResponse.getView(VIEW_PATH);
    }

    private HttpResponse doPost(RequestBody requestBody) {
        Map<String, String> body = requestBody.getContents();
        String userId = body.get("userId");
        String password = body.get("password");

        User user = DataBase.findUserById(userId);

        if (user.getPassword().equals(password)) {
            HttpResponse successResponse = HttpResponse.redirect(successRedirectPath);
            successResponse.getResponseHeader().setCookie(LOGIN_SUCCESS_COOKIE);
            return successResponse;
        }

        HttpResponse failResponse = HttpResponse.redirect(failRedirectPath);
        failResponse.getResponseHeader().setCookie(LOGIN_FAIL_COOKIE);
        return failResponse;
    }
}
