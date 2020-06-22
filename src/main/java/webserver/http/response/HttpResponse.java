package webserver.http.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static webserver.http.HttpHeader.SET_COOKIE;

@NoArgsConstructor
@Getter
public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private ResponseLine responseLine = new ResponseLine();
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private ResponseBody responseBody = new ResponseBody();

    public void addCookie(String value) {
        responseHeaders.addHeader(SET_COOKIE, value, "Path=/");
    }

    public void response(DataOutputStream dos) throws IOException {
        responseLine(dos);
        responseHeader(dos);
        responseBody(dos);
    }

    private void responseLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(responseLine.response());
    }

    private void responseHeader(DataOutputStream dos) {
        Map<HttpHeader, ResponseHeader> headers = responseHeaders.getResponseHeaders();
        try {
            for (HttpHeader name : headers.keySet()) {
                dos.writeBytes(name.getName() + ": " + headers.get(name));
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        byte[] file = responseBody.getFile();
        if (Objects.isNull(file)) {
            return;
        }

        try {
            dos.write(file, 0, file.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
