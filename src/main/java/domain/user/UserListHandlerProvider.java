package domain.user;

import view.ViewCompiler;
import webserver.http.RequestMethod;
import webserver.handler.Condition;
import webserver.handler.PatternMatchHandlerProvider;

public class UserListHandlerProvider extends PatternMatchHandlerProvider {

    private static final String PATH_PATTERN = "/user/list";
    private static final Condition CONDITION = request -> request.matchPath(PATH_PATTERN) &&
            request.matchMethod(RequestMethod.GET);

    public UserListHandlerProvider(final ViewCompiler viewCompiler) {
        super(CONDITION, new UserListHandler(viewCompiler));
    }
}
