package http.view;

import http.Headers;
import http.HttpStatus;
import java.util.Objects;

public class RedirectView implements View{

    private String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus._302;
    }

    @Override
    public byte[] getBody() {
        return new byte[0];
    }

    @Override
    public Headers getHeaders() {
        Headers headers = new Headers();
        headers.add("Location", path);
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RedirectView that = (RedirectView) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
