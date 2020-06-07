package http.response;

import http.Headers;
import http.view.View;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);


    private final Headers headers = new Headers(new HashMap<>());
    private final Map<String, String> cookies = new HashMap<>();
    private final View view;

    public HttpResponse(View view) {
        this.view = view;
    }

    public void addCookie(String name, String value) {
        this.cookies.put(name, value);
    }

    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        responseToStatus(dos);
        responseToHeader(dos);

        view.response(out);

        dos.flush();
    }

    private void responseToHeader(DataOutputStream dos) throws IOException {
        this.headers.response(dos);

        this.cookies.forEach((name, value) -> {
            try {
                dos.writeBytes(String.format("Set-Cookie: %s=%s; Path=/\r\n", name, value));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    private void responseToStatus(DataOutputStream dos) throws IOException {
        String responseLine = String.format("HTTP/1.1 %s", view.getHttpStatus().toString());
        dos.writeBytes(responseLine + "\r\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(headers, that.headers) &&
            Objects.equals(cookies, that.cookies) &&
            Objects.equals(view, that.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, cookies, view);
    }
}
