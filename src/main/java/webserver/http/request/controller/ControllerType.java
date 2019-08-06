package webserver.http.request.controller;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public enum ControllerType {
    LOGIN("/user/login", LoginController::new),
    JOIN("/user/create", JoinController::new),
    ;

    private final ControllerCreator creator;
    private String url;

    ControllerType(String url, ControllerCreator creator) {
        this.url = url;
        this.creator = creator;
    }

    public String getUrl() {
        return url;
    }

    public Controller getController() {
        return creator.create();
    }

    @FunctionalInterface
    public interface ControllerCreator {
        Controller create();
    }
}
