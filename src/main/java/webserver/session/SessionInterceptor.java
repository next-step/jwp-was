package webserver.session;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class SessionInterceptor implements Interceptor {

    @Override
    public boolean preHandle(HttpRequest request, HttpResponse response) {
        if (!request.sessionIdPresentInCookie()) {
            response.addHeader("Set-Cookie", HttpSessionStorage.JSESSIONID + "=" +
                    HttpSessionStorage.generateRandomId());
        }
        return true;
    }
}
