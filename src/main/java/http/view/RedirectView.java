package http.view;

import http.HttpStatus;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class RedirectView implements View {

    private String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus._302;
    }

    @Override
    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes("Location: " + this.path);
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
