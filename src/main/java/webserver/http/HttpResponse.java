package webserver.http;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private DataOutputStream dos;
    private Map<String, String> cookies;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        cookies = new HashMap<>();
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public DataOutputStream getDataOutputStream() {
        return dos;
    }

}
