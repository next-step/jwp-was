package http.response;

import http.Headers;
import http.view.View;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {

    private final Headers headers = new Headers(new HashMap<>());
    private final Map<String, String> cookies = new HashMap<>();
    private final View view;

    public HttpResponse(View view){
        this.view =view;
    }

    public void addCookie(String name, String value) {
        this.cookies.put(name, value);
    }

    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String responseLine = String.format("HTTP/1.1 %s", view.getHttpStatus().toString());
        dos.writeBytes( responseLine+ "\r\n");

        List<String> headerLines = this.headers.toLines();
        headerLines.addAll(this.view.getHeaders().toLines());
        for (String headerLine : headerLines) {
            dos.writeBytes(headerLine);
            dos.writeBytes("\r\n");
        }

        byte[] body = this.view.getBody();
        dos.writeBytes("Content-Length: " + body.length + "\r\n");

        this.cookies.forEach(
            (name, value) -> {
                try {
                    dos.writeBytes(String.format("Set-Cookie: %s=%s; Path=/\r\n", name, value));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
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
