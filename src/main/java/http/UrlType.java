package http;


public enum UrlType {
    INDEX("/index.html"),
    CREATE_USER("/user/create"),
    LOGIN_USER("/user/login"),
    LIST("/user/list")
    ;
    private String urlPath;

    UrlType(String urlPath) {
        this.urlPath = urlPath;
    }

    public static UrlType of(String urlPath) {
        if (UrlType.INDEX.getUrlPath().equals(urlPath))
            return UrlType.INDEX;
        else if (UrlType.LOGIN_USER.getUrlPath().equals(urlPath))
            return UrlType.LOGIN_USER;
        else if (UrlType.CREATE_USER.getUrlPath().equals(urlPath))
            return UrlType.CREATE_USER;
        else if (UrlType.LIST.getUrlPath().equals(urlPath))
            return UrlType.LIST;
        return UrlType.INDEX;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
