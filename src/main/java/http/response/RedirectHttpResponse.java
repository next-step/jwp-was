package http.response;

import http.Headers;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;


public class RedirectHttpResponse implements HttpResponse{

    private final String path;

    public RedirectHttpResponse(String path) {
        this.path = path;
    }

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }

    @Override
    public Headers getHeaders() {
        String location = "Location: " + path;
        return Headers.from(Arrays.asList(location));
    }

    @Override
    public String getStatusMessage() {
        return "302 FOUND";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RedirectHttpResponse that = (RedirectHttpResponse) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
