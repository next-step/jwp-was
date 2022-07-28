package webserver.request;

public enum RequestIndex {
    GET_INDEX_HTML ("/index.html"),
    GET_USER_FORM_HTML ("/user/form.html"),
    USER_CREATE ("/user/create"),
    LOGIN_FAIL("/user/login_failed.html"),
    USER_LIST  ("/user/list");

    private final String path;

    RequestIndex(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
