package domain.user;

import webserver.http.RequestMethod;
import webserver.handler.Condition;
import webserver.handler.Handler;
import webserver.handler.PatternMatchHandlerProvider;

public class LoginHandlerProvider extends PatternMatchHandlerProvider {

    private static final String PATH_PATTERN = "/user/login";
    private static final Condition CONDITION = request -> request.matchPath(PATH_PATTERN) &&
            request.matchMethod(RequestMethod.POST);
    private static final Handler HANDLER = new LoginHandler(new LoginSuccessHandler(), new LoginFailHandler());

    public LoginHandlerProvider() {
        super(CONDITION, HANDLER);
    }
}
