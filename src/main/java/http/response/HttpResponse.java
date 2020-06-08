package http.response;

import http.Headers;
import http.Protocol;
import http.view.View;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class HttpResponse {

    private final Headers headers = Headers.newInstance();
    private final View view;

    public HttpResponse(View view) {
        this.view = view;
    }

    public void addCookie(String name, String value) {
        this.headers.addCookie(name, value);
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

    }

    private void responseToStatus(DataOutputStream dos) throws IOException {
        String responseLine = String
            .format("%s %s", Protocol.HTTP_1_1, view.getHttpStatus().toString());
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
            Objects.equals(view, that.view);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, view);
    }
}
