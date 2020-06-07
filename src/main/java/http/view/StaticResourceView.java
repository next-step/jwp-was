package http.view;

import http.Headers;
import http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import utils.FileIoUtils;
import utils.HttpUtils;

public class StaticResourceView implements View {

    private final String path;

    public StaticResourceView(String path) {
        this.path = path;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus._200;
    }

    @Override
    public byte[] getBody() throws IOException {
        return FileIoUtils.loadFile(getFile());
    }

    @Override
    public Headers getHeaders() {
        Headers headers = new Headers();
        headers.add("Content-Type", HttpUtils.getMimeType(this.getFile().getName()));
        return headers;
    }

    private File getFile() {
        try {
            return FileIoUtils.getFileFromClasspath(this.path);
        } catch (URISyntaxException e) {
            return null;
        }
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
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
