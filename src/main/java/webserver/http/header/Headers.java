package webserver.http.header;

import webserver.http.cookie.Cookies;

import java.util.List;

public interface Headers {

    void add(final String key, final String value);
    void setLocation(final String redirectPath);
    void setContentLength(final int contentLength);
    Cookies getCookies();
    String getString(final String key);
    int getContentLength();
    boolean isEmpty();
    List<HttpHeader> toList();
}
