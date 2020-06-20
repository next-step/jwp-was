package webserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHeaders {

    private static final Logger log = LoggerFactory.getLogger(ResponseHeaders.class);

    private Map<HttpResponseHeader, ResponseHeader> responseHeaders = new HashMap<>();

    public void addHeader(HttpResponseHeader name, String... values) {
        ResponseHeader responseHeader = ResponseHeader.of(name, values);
        responseHeaders.put(name, responseHeader);
    }

    public void response(DataOutputStream dos) {
        try {
            for (HttpResponseHeader name : responseHeaders.keySet()) {
                dos.writeBytes(name + ": " + responseHeaders.get(name));
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
