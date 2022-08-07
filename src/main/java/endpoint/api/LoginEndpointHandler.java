package endpoint.api;

import application.LoginUserCommand;
import application.LoginUserService;
import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;
import webserver.http.session.HttpSession;

import static application.LoginUserService.LOGINED_FLAG;

public class LoginEndpointHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT_PATH = "/user/login";
    private static final String LOGIN_SUCCESS_REDIRECT_ENDPOINT_PATH = "/index.html";
    private static final String LOGIN_FAILURE_REDIRECT_ENDPOINT_PATH = "/user/login_failed.html";

    public LoginEndpointHandler() {
        super(new Endpoint(HttpMethod.POST, ENDPOINT_PATH));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {

        String userId = httpRequestMessage.getBodyValue("userId");
        String password = httpRequestMessage.getBodyValue("password");

        boolean isPossibleLogin = LoginUserService.isPossibleLogin(new LoginUserCommand(userId, password));

        String redirectPath = LOGIN_FAILURE_REDIRECT_ENDPOINT_PATH;

        if (isPossibleLogin) {
            HttpSession session = httpRequestMessage.getSession();
            session.setAttribute(LOGINED_FLAG, true);
            redirectPath = LOGIN_SUCCESS_REDIRECT_ENDPOINT_PATH;
        }

        return HttpResponseMessage.redirect(redirectPath);
    }
}
