package webserver.response;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class HttpResponseBody {

    private final byte[] body;

    private HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public static HttpResponseBody of(String body) {
        Objects.requireNonNull(body);
        return new HttpResponseBody(body.getBytes(StandardCharsets.UTF_8));
    }

    public static HttpResponseBody of(byte[] body) {
        Objects.requireNonNull(body);
        return new HttpResponseBody(body);
    }

    public static HttpResponseBody createEmpty() {
        return new HttpResponseBody(new byte[0]);
    }

    public byte[] get() {
        return body;
    }

    public String bodyString() {
        return new String(body);
    }

    public int length() {
        return body.length;
    }

}
