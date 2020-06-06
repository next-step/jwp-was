package http.view;

import http.Headers;
import http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import utils.FileIoUtils;

public class StaticResourceView implements View {

    private final File file;

    public StaticResourceView(String path) {
        this.file = new File("./templates" + path);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus._200;
    }

    @Override
    public byte[] getBody() throws IOException {
        try {
            return FileIoUtils.loadFileFromClasspath(file.getPath());
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Headers getHeaders() {
        Headers headers = new Headers();
        headers.add("Content-Type", "text/html;charset=utf-8");
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
        StaticResourceView that = (StaticResourceView) o;
        return Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
