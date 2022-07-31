package webserver.response;

import enums.HttpStatusCode;
import java.nio.charset.StandardCharsets;
import java.util.List;
import utils.JsonUtils;
import webserver.request.HttpHeader;

public class HttpResponse {
    private final HttpStatusCode code;
    private final HttpHeader headers;
    private final byte[] body;

    public HttpResponse(HttpStatusCode code) throws Exception {
        this(code, new HttpHeader(), List.of());
    }

    public HttpResponse(HttpStatusCode code, byte[] body) throws Exception {
        this(code, new HttpHeader(), body);
    }

    public HttpResponse(HttpStatusCode code, HttpHeader headers, byte[] body) {
        this.code = code;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse(HttpStatusCode code, Object body) throws Exception {
        this.body = JsonUtils.convertObjectToJsonString(body).getBytes(StandardCharsets.UTF_8);
        this.headers = new HttpHeader();
        this.code = code;
    }

    public HttpResponse(HttpStatusCode code, HttpHeader headers, Object body) throws Exception {
        this.body = JsonUtils.convertObjectToJsonString(body).getBytes(StandardCharsets.UTF_8);
        this.headers = headers;
        this.code = code;
    }

    public HttpStatusCode getStatusCode() {
        return code;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpHeader getHeaders() {
        return headers;
    }
}
