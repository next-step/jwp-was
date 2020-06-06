package http.response;

import http.Headers;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import utils.FileIoUtils;

public class StaticResourceHttpResponse implements HttpResponse {

    private File file;

    public StaticResourceHttpResponse(File file) {
        this.file = file;
    }

    @Override
    public byte[] getBody() throws IOException {
        try {
            return FileIoUtils.loadFileFromClasspath(file.getPath());
        } catch (URISyntaxException e) {
            throw new IOException("file path invalid fail");
        }
    }

    @Override
    public Headers getHeaders() {
        String contentType = "Content-Type: text/html;charset=utf-8";
        return Headers.from(Arrays.asList(contentType));
    }

    @Override
    public String getStatusMessage() {
        return "200 OK";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StaticResourceHttpResponse that = (StaticResourceHttpResponse) o;
        return Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
