package webserver.session;

import http.request.HttpRequest;
import http.response.HttpResponse;
import mvc.controller.Controller;

import java.io.IOException;
import java.net.URISyntaxException;

public class SessionInterceptor implements Interceptor {

    private Controller controller;

    private SessionInterceptor(Controller controller) {
        this.controller = controller;
    }

    public static SessionInterceptor from(Controller controller) {
        return new SessionInterceptor(controller);
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (!request.sessionIdPresentInCookie()) {
            response.addHeader("Set-Cookie", HttpSessionStorage.JSESSIONID + "=" +
                    HttpSessionStorage.generateRandomId());
        }
        controller.service(request, response);
    }
}
