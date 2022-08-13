package webserver.http.request.body;

import org.apache.logging.log4j.util.Strings;

public class HttpRequestBody {

    private final String bodyValue;

    public HttpRequestBody(String bodyValue) {
        this.bodyValue = bodyValue;
    }

    public static HttpRequestBody emptyBody() {
        return new HttpRequestBody(Strings.EMPTY);
    }

    public String getBodyValue() {
        return bodyValue;
    }
}
