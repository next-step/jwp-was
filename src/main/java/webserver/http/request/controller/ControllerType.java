package webserver.http.request.controller;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public enum ControllerType {
    LOGIN("/user/login", LoginController::new, true),
    JOIN("/user/create", JoinController::new, true),
    USER_LIST("/user/list", UserListController::new, false),
    DEFAULT_ERROR("/error", ErrorController::new, true),
    ;

    private final ControllerCreator creator;
    private String url;
    private boolean allowAll;

    ControllerType(String url, ControllerCreator creator, boolean allowAll) {
        this.url = url;
        this.creator = creator;
        this.allowAll = allowAll;
    }

    public String getUrl() {
        return url;
    }

    public Controller getController() {
        return creator.create(this.allowAll);
    }

    @FunctionalInterface
    public interface ControllerCreator {
        Controller create(boolean allowAll);
    }
}
