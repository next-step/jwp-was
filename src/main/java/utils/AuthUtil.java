package utils;

import com.google.common.net.HttpHeaders;
import db.DataBase;
import http.SessionAttribute;
import http.httprequest.HttpRequest;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import http.httpresponse.ResponseHeader;
import http.httpresponse.StatusLine;
import model.User;

import java.util.Map;

public class AuthUtil {
    private AuthUtil() {
        throw new AssertionError("'Assert' can not be instanced");
    }

    public static HttpResponse login(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest.getBodyValue("userId"));

        if(user == null || user.isNotMatchPassword(httpRequest.getBodyValue("password")) ) {
            return new HttpResponse(
                    new StatusLine(HttpStatusCode.FOUND),
                    new ResponseHeader(Map.of(
                            HttpHeaders.LOCATION, "/user/login_failed.html",
                            HttpHeaders.SET_COOKIE, "logined=false; Path=/"
                    ))
            );
        }

        return HttpResponse.sendRedirect(
                "/index.html",
                new SessionAttribute(Map.of("user", user))
        );
    }

    public HttpResponse logout(HttpRequest httpRequest) {
        return new HttpResponse(
                new StatusLine(HttpStatusCode.FOUND),
                new ResponseHeader(Map.of(
                        HttpHeaders.LOCATION, "/index.html",
                        HttpHeaders.SET_COOKIE, "logined=false; Path=/"
                ))
        );
    }

    public static boolean isNotLoggedIn(HttpRequest httpRequest) {
        return httpRequest.getSession().doesNotContainAttribute("user");
    }

}
