package endpoint.page;

import endpoint.HttpRequestHandler;
import webserver.http.header.HttpCookie;
import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;

public class PreLoginValidationFromCookieProxyHandler implements HttpRequestHandler {
    private static final String LOGIN_PAGE_ENDPOINT = "/user/login.html";
    private HttpRequestHandler originHandler;

    public PreLoginValidationFromCookieProxyHandler(HttpRequestHandler originHandler) {
        this.originHandler = originHandler;
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {

        HttpCookie httpCookie = httpRequestMessage.getCookie("logined");

        boolean isNotLogined = !Boolean.parseBoolean(httpCookie.getCookieValue());

        if (isNotLogined) {
            return HttpResponseMessage.redirect(LOGIN_PAGE_ENDPOINT);
        }

        return originHandler.handle(httpRequestMessage);
    }
}
