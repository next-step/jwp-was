package utils;

import com.google.common.net.HttpHeaders;
import db.DataBase;
import http.httprequest.HttpRequest;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import http.httpresponse.ResponseHeader;
import model.User;

import java.util.Collections;
import java.util.Map;

public class AuthUtil {
    private AuthUtil() {
        throw new AssertionError("'Assert' can not be instanced");
    }

    public static HttpResponse login(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest.getBodyValue("userId"));

        if(user == null || user.isNotMatchPassword(httpRequest.getBodyValue("password")) ) {
            return new HttpResponse(
                    HttpStatusCode.FOUND,
                    new ResponseHeader(Map.of(
                            HttpHeaders.LOCATION, "/user/login_failed.html",
                            HttpHeaders.SET_COOKIE, "logined=false; Path=/"
                    ))
            );
        }

        return HttpResponse.sendRedirect(
                "/index.html",
                new ResponseHeader(Collections.singletonMap(HttpHeaders.SET_COOKIE, "logined=true; Path=/"))
        );
    }

    public HttpResponse logout(HttpRequest httpRequest) {
        return new HttpResponse(
                HttpStatusCode.FOUND,
                new ResponseHeader(Map.of(
                        HttpHeaders.LOCATION, "/index.html",
                        HttpHeaders.SET_COOKIE, "logined=false; Path=/"
                ))
        );
    }

    public static boolean isLoggedIn(HttpRequest httpRequest) {
        return httpRequest.getCookie().contains("logined=true");
    }

}
