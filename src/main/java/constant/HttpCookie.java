package constant;

public enum HttpCookie {
    LOGIN("logined"),
    JSESSIONID("JSESSIONID");

    private String value;

    HttpCookie(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
