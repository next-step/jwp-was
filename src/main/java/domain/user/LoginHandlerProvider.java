package domain.user;

import webserver.handler.Condition;
import webserver.handler.Handler;
import webserver.handler.PatternMatchHandlerProvider;
import webserver.http.RequestMethod;

public class LoginHandlerProvider extends PatternMatchHandlerProvider {

    private static final String PATH_PATTERN = "/user/login";
    private static final Condition CONDITION = request -> request.matchPath(PATH_PATTERN) &&
            request.matchMethod(RequestMethod.POST);
    private static final Handler HANDLER = new LoginHandler();

    public LoginHandlerProvider() {
        super(CONDITION, HANDLER);
    }
}
