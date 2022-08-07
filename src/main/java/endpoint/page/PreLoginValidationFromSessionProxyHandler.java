package endpoint.page;

import endpoint.HttpRequestHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;
import webserver.http.session.HttpSession;

import static application.LoginUserService.LOGINED_FLAG;

public class PreLoginValidationFromSessionProxyHandler implements HttpRequestHandler {
    private static final String LOGIN_PAGE_ENDPOINT = "/user/login.html";
    private HttpRequestHandler originHandler;

    public PreLoginValidationFromSessionProxyHandler(HttpRequestHandler originHandler) {
        this.originHandler = originHandler;
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {

        HttpSession httpSession = httpRequestMessage.getSession();

        Object loginedAttribute = httpSession.getAttribute(LOGINED_FLAG);

        boolean isLogined = loginedAttribute != null && (boolean) loginedAttribute;

        if (!isLogined) {
            return HttpResponseMessage.redirect(LOGIN_PAGE_ENDPOINT);
        }

        return originHandler.handle(httpRequestMessage);
    }
}
