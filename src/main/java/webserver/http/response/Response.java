package webserver.http.response;

import webserver.http.cookie.Cookie;
import webserver.http.cookie.Cookies;
import webserver.http.header.Headers;
import webserver.http.protocol.Protocol;

import java.util.HashMap;

public class Response {
    private final Status status;
    private final Headers headers;
    private final Cookies addedCookies;
    private byte[] body;

    public Response(Status status) {
        this(status, new Headers(new HashMap<>()), new Cookies(new HashMap<>()), null);
    }

    public Response(Status status, Headers headers, Cookies addedCookies, byte[] body) {
        this.status = status;
        this.headers = headers;
        this.addedCookies = addedCookies;
        this.body = body;
    }

    public static Response ok() {
        return new Response(new Status(Protocol.http1Point1(), StatusCode.OK));
    }

    public static Response sendRedirect(String location) {
        Response response = new Response(new Status(Protocol.http1Point1(), StatusCode.FOUND));
        response.headers.add("Location", location);
        return response;
    }

    public static Response from(StatusCode statusCode) {
        return new Response(new Status(Protocol.http1Point1(), statusCode));
    }

    public void addHeader(String name, String value) {
        headers.add(name, value);
    }

    public void addBody(String body) {
        this.body = body.getBytes();
        this.headers.add("Content-Length", String.valueOf(body.length()));
    }

    public void addCookie(Cookie cookie) {
        addedCookies.addCookie(cookie);
    }
}
