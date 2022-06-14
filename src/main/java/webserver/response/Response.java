package webserver.response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 현재 DTO느낌으로 사용하고 있으나 오브젝트로 역할을 가져가야한다.
 */
public class Response {

    private final byte[] body;
    private final String code;
    private final String location;
    private final Map<String, String> cookieMap;

    public Response(byte[] body, String code, String location, Map<String, String> cookieMap) {
        this.body = body;
        this.code = code;
        this.location = location;
        this.cookieMap = cookieMap;
    }

    public static Response response202() {
        return new Response(new byte[]{}, "202", null, new HashMap<>());
    }

    public static Response response302(String location) {
        return new Response(new byte[]{}, "302", location, new HashMap<>());
    }

    public byte[] getBody() {
        return body;
    }

    public String getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public String parseCookie() {
        return cookieMap.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(";"));
    }

    public void addCookie(String key, String value) {
        cookieMap.put(key, value);
    }
}
