package endpoint.api;

import application.LoginUserCommand;
import application.LoginUserService;
import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import utils.TemplatePageLoader;
import webserver.http.header.HttpCookie;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.*;

public class LoginEndpointHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT_PATH = "/user/login";
    private static final String LOGIN_SUCCESS_REDIRECT_ENDPOINT_PATH = "/index.html";
    private static final String LOGIN_FAILURE_REDIRECT_ENDPOINT_PATH = "/user/login_failed.html";
    private static final String LOGINED_COOKIE_KEY = "logined";

    public LoginEndpointHandler() {
        super(new Endpoint(HttpMethod.POST, ENDPOINT_PATH));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {

        String userId = httpRequestMessage.getBodyValue("userId");
        String password = httpRequestMessage.getBodyValue("password");

        boolean isPossibleLogin = LoginUserService.isPossibleLogin(new LoginUserCommand(userId, password));

        return createLoginedHttpResponseMessage(isPossibleLogin);
    }

    private HttpResponseMessage createLoginedHttpResponseMessage(boolean isPossibleLogin) {
        String redirectPath = isPossibleLogin ? LOGIN_SUCCESS_REDIRECT_ENDPOINT_PATH : LOGIN_FAILURE_REDIRECT_ENDPOINT_PATH;

        HttpResponseHeaders httpResponseHeaders = HttpResponseHeaders.ofLocation(redirectPath);
        httpResponseHeaders.addCookie(new HttpCookie(LOGINED_COOKIE_KEY, Boolean.toString(isPossibleLogin)));
        httpResponseHeaders.addCookie(HttpCookie.ALL_ALLOWED_PATH);

        return new HttpResponseMessage(
                HttpResponseStatusLine.fromOnePointOne(HttpStatus.FOUND),
                new HttpResponseBody(TemplatePageLoader.getTemplatePage(redirectPath)),
                httpResponseHeaders
        );
    }
}
