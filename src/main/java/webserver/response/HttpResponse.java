package webserver.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.HttpStatusCode;
import java.nio.charset.StandardCharsets;
import utils.JsonUtils;

public class HttpResponse {
    private final HttpStatusCode code;
    private final byte[] body;
    
    public HttpResponse(HttpStatusCode code, byte[] body) {
        this.code = code;
        this.body = body;
    }

    public HttpResponse(HttpStatusCode code, Object body) throws Exception {
        this.body = JsonUtils.convertObjectToJsonString(body).getBytes(StandardCharsets.UTF_8);
        this.code = code;
    }

    public Integer getStatusCode() {
        return code.getCode();
    }

    public byte[] getBody() {
        return body;
    }
}
