package webserver.http;

import utils.StringUtils;

public class HttpCookie {
	
	private static final String COOKIE_PATH = "Path";
	private static final String DEFAULT_COOKIE_PATH = "/";

    private final String name;

    private final String value;
    
    private final String path;

    public HttpCookie(String name, String value) {
        this(name, value, DEFAULT_COOKIE_PATH);
    }
    
    public HttpCookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getCookieSetLine() {
    	StringBuilder sb = new StringBuilder()
    	.append(this.name)
    	.append(HttpCookies.COOKIE_SPLIT_SIGN)
    	.append(this.value)
    	.append(HttpCookies.COOKIES_SPLIT_SIGN)
    	.append(StringUtils.getWhiteSpace())
    	.append(COOKIE_PATH)
    	.append(HttpCookies.COOKIE_SPLIT_SIGN)
    	.append(this.path)
    	;
        return sb.toString();
    }
}
