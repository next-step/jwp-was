package http;

import http.httprequest.HttpRequest;
import http.httpresponse.HttpHeaders;
import http.httpresponse.HttpResponse;
import http.httpresponse.ResponseCookie;
import webserver.controller.Controller;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpSessionInterceptor {
    private final Controller controller;

    public HttpSessionInterceptor(Controller controller) {
        this.controller = controller;
    }

    public HttpResponse execute(HttpRequest httpRequest) throws IOException, URISyntaxException {
        HttpResponse response = controller.execute(httpRequest);

        if(response.isEmptySessionAttribute()) {
            return response;
        }

        HttpSession httpSession = httpRequest.getSession();
        httpSession.addAll(response.getSessionAttribute());
        SessionStorage.getInstance().add(httpSession);

        return response.addHeader(
                HttpHeaders.SET_COOKIE,
                new ResponseCookie(SessionStorage.getSessionId(), httpSession.getId())
        );
    }
}
