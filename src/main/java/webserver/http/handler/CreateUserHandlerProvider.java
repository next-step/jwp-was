package webserver.http.handler;

import webserver.http.RequestMethod;

public class CreateUserHandlerProvider extends PatternMatchHandlerProvider {

    private static final String PATH_PATTERN = "/user/create";
    private static final Condition CONDITION = request -> request.matchPath(PATH_PATTERN) &&
            request.matchMethod(RequestMethod.POST);
    private static final CreateUserHandler HANDLER = new CreateUserHandler();

    public CreateUserHandlerProvider() {
        super(CONDITION, HANDLER);
    }
}
