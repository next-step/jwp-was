package webserver.http;

import webserver.controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseCookie;

public final class HttpSessionInterceptor {

    private static final SessionStorage STORAGE = SessionStorage.empty();
    private static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";

    private final Controller controller;

    public HttpSessionInterceptor(Controller controller) {
        this.controller = controller;
    }

    public HttpResponse execute(HttpRequest request) throws Exception {
        HttpSession requestSession = requestSession(request);
        HttpResponse response = controller.execute(request, requestSession);
        if (response.emptySessionAttribute()) {
            return response;
        }
        requestSession.addAll(response.sessionAttribute());
        STORAGE.add(requestSession);
        return response.addHeader(
                HttpHeaders.SET_COOKIE,
                ResponseCookie.of(DEFAULT_SESSION_COOKIE_NAME, requestSession.getId())
        );
    }

    private HttpSession requestSession(HttpRequest request) {
        return request.cookieValue(DEFAULT_SESSION_COOKIE_NAME)
                .flatMap(STORAGE::find)
                .orElseGet(HttpSession::empty);
    }
}
