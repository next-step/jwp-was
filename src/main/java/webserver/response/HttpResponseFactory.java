package webserver.response;

import webserver.request.Headers;
import webserver.request.Protocol;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseFactory {

    public static HttpResponse response202() {
        return new HttpResponse(new byte[]{}, "202", Headers.empty(), Protocol.defaultProtocol());
    }

    public static HttpResponse response202(byte[] body) {
        return new HttpResponse(body, "202", Headers.empty(), Protocol.defaultProtocol());
    }

    public static HttpResponse response302(String location) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(Headers.LOCATION, location);
        return new HttpResponse(new byte[]{}, "302", new Headers(headerMap), Protocol.defaultProtocol());
    }

    public static HttpResponse response404() {
        return new HttpResponse(new byte[]{}, "404", Headers.empty(), Protocol.defaultProtocol());
    }

}
