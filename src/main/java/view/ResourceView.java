package view;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceView implements View {

    private final static String SLASH = "/";
    private final static String DEFAULT_FILE_PATH = "index.html";
    private final static String DEFAULT_VIEW_PREFIX = "templates";
    private final static String DEFAULT_VIEW_SUFFIX = ".html";

    @Getter
    private String path;

    private byte[] responseBody;

    public ResourceView(final String path) throws IOException, URISyntaxException {
        setPath(path);
        setResponseBody();
    }

    private void setPath(final String path) {
        String convertedPath = path;
        if (StringUtils.isBlank(path) || StringUtils.equals(path, SLASH)) {
            convertedPath = SLASH + DEFAULT_FILE_PATH;
        }

        if (!StringUtils.startsWith(convertedPath, SLASH)) {
            convertedPath = SLASH + convertedPath;
        }

        if (!StringUtils.endsWith(convertedPath, DEFAULT_VIEW_SUFFIX)) {
            convertedPath = convertedPath + DEFAULT_VIEW_SUFFIX;
        }

        this.path = convertedPath;
    }

    private void setResponseBody() throws IOException, URISyntaxException {
        this.responseBody = FileIoUtils.loadFileFromClasspath(DEFAULT_VIEW_PREFIX + this.path);
    }

    @Override
    public byte[] responseBody() {
        return responseBody;
    }

    public int contentLength() {
        return responseBody.length;
    }
}
