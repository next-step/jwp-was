package http.view;

import http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import utils.FileIoUtils;

public class StaticResourceView extends FileResourceView {

    private final String path;

    public StaticResourceView(String path) {
        this.path = path;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus._200;
    }

    @Override
    protected BodyFile getBodyFile() throws IOException {
        String fileName = new File(path).getName();
        try {
            return new BodyFile(fileName, FileIoUtils.loadFileFromClasspath(this.path));
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage(), e);
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
