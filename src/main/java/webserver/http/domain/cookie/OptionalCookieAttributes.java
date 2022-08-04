package webserver.http.domain.cookie;

import java.util.Date;

class OptionalCookieAttributes {

    static final String PATH = "Path";
    static final String MAX_AGE = "Max-Age";
    static final String EXPIRES = "Expires";
    static final String DOMAIN = "Domain";
    static final String SAME_SITE = "SameSite";
    static final String HTTP_ONLY = "HttpOnly";
    static final String SECURE = "Secure";

    private static final String DEFAULT_PATH = "/";

    private String path = DEFAULT_PATH;
    private Integer maxAge;
    private Date expires;
    private String domain;
    private String sameSite;
    private boolean httpOnly;
    private boolean secure;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSameSite() {
        return sameSite;
    }

    public void setSameSite(String sameSite) {
        this.sameSite = sameSite;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }
}
