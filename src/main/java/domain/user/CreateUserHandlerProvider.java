package domain.user;

import webserver.http.RequestMethod;
import webserver.handler.Condition;
import webserver.handler.Handler;
import webserver.handler.PatternMatchHandlerProvider;

public class CreateUserHandlerProvider extends PatternMatchHandlerProvider {

    private static final String PATH_PATTERN = "/user/create";
    private static final Condition CONDITION = request -> request.matchPath(PATH_PATTERN) &&
            request.matchMethod(RequestMethod.POST);
    private static final Handler HANDLER = new CreateUserHandler();

    public CreateUserHandlerProvider() {
        super(CONDITION, HANDLER);
    }
}
