package webserver.servlet;

import db.DataBase;
import dto.SignInUser;
import exception.NotFoundUserException;
import model.User;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class SignInServlet extends HttpServlet {

    private static final String API_PATH = "/user/login";
    private static final String SIGN_IN_SUCCESS_PATH = "/index.html";
    private static final String SIGN_IN_SUCCESS_COOKIE = "logined=true; Path=/";

    private static final String SIGN_IN_FAILED_PATH = "/user/login_failed.html";
    private static final String SIGN_IN_FAILED_COOKIE = "logined=false; Path=/";

    @Override
    public String getRequestPath() {
        return API_PATH;
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        Map<String, String> requestBody = request.getBody().getContents();

        SignInUser signInUser = convertRequestBodyToSignInUser(requestBody);

        User user = DataBase.findUserById(signInUser.getUserId())
                .orElseThrow(() -> new NotFoundUserException(signInUser.getUserId()));

        if (user.verifyPassword(signInUser.getPassword())) {
            HttpHeaders httpHeaders = HttpHeaders.redirect(SIGN_IN_SUCCESS_PATH);
            httpHeaders.addResponseHeader(HttpHeader.SET_COOKIE, SIGN_IN_SUCCESS_COOKIE);

            return HttpResponse.redirect(httpHeaders);
        }

        HttpHeaders httpHeaders = HttpHeaders.redirect(SIGN_IN_FAILED_PATH);
        httpHeaders.addResponseHeader(HttpHeader.SET_COOKIE, SIGN_IN_FAILED_COOKIE);

        return HttpResponse.redirect(httpHeaders);
    }

    private SignInUser convertRequestBodyToSignInUser(Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        return new SignInUser(userId, password);
    }
}
