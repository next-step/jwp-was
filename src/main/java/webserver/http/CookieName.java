package webserver.http;

public enum CookieName {

    LOGINED("logined"),
    SESSION_ID("SessionId")
    ;

    private final String cookieName;

    CookieName(final String cookieName) {
        this.cookieName = cookieName;
    }

    @Override
    public String toString() {
        return cookieName;
    }
}
