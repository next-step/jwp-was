package view;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticView implements View {

    private final static String STATIC_VIEW_PREFIX = "static";
    private final static String TEMPLATES_VIEW_PREFIX = "templates";

    @Getter
    private String path;

    private byte[] responseBody;

    public StaticView(String path) throws IOException, URISyntaxException {
        setPath(path);
        setResponseBody();
    }

    private void setPath(final String path) {
        String convertedPath = path;

        if (!StringUtils.startsWith(convertedPath, SLASH)) {
            convertedPath = SLASH + convertedPath;
        }

        this.path = convertedPath;
    }

    private void setResponseBody() throws IOException, URISyntaxException {
        final String staticFilePath = STATIC_VIEW_PREFIX + this.path;
        if (FileIoUtils.existsFileFromClasspath(staticFilePath)) {
            this.responseBody = FileIoUtils.loadFileFromClasspath(staticFilePath);
            return;
        }

        this.responseBody = FileIoUtils.loadFileFromClasspath(TEMPLATES_VIEW_PREFIX + this.path);
    }

    @Override
    public byte[] responseBody() {
        return responseBody;
    }

    @Override
    public int contentLength() {
        return responseBody.length;
    }
}
