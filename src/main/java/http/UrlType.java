package http;

import org.springframework.util.StringUtils;

public enum UrlType {
    INDEX("/index.html"),
    CREATE_USER("/user/create"),
    LOGIN_USER("/user/login")
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
        return UrlType.INDEX;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
