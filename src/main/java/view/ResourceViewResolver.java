package view;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Getter
public class ResourceViewResolver implements ViewResolver {

    private final static String SLASH = "/";
    private final static String DEFAULT_FILE_PATH = "index.html";
    private final static String DEFAULT_VIEW_PREFIX = "templates";
    private final static String DEFAULT_VIEW_SUFFIX = ".html";

    private String path;
    private byte[] response;

    public ResourceViewResolver(final String path) throws IOException, URISyntaxException {
        setPath(path);
        setResponse();
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

    private void setResponse() throws IOException, URISyntaxException {
        this.response = FileIoUtils.loadFileFromClasspath(DEFAULT_VIEW_PREFIX + this.path);
    }
}
