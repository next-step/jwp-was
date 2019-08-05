package view;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Getter
public class ResourceViewResolver implements ViewResolver {

    private final static String SLASH = "/";
    private final static String REDIRECT = "redirect:";
    private final static String DEFAULT_FILE_PATH = "index.html";
    private final static String DEFAULT_VIEW_PREFIX = "templates";
    private final static String DEFAULT_VIEW_SUFFIX = ".html";

    private String path;
    private byte[] responseBody;
    private boolean isRedirect = false; // Response로 분리할 때 제거할 것

    public ResourceViewResolver(final String path) throws IOException, URISyntaxException {
        setPath(path);
        setResponseBody(path);
    }

    private void setPath(final String path) {
        String convertedPath = path;
        if (StringUtils.isBlank(path) || StringUtils.equals(path, SLASH)) {
            convertedPath = SLASH + DEFAULT_FILE_PATH;
        }

        if (startsWithRedirect(path)) {
            this.isRedirect = true;
            convertedPath = StringUtils.removeStart(path, REDIRECT);
        }

        if (!StringUtils.startsWith(convertedPath, SLASH)) {
            convertedPath = SLASH + convertedPath;
        }

        if (!StringUtils.endsWith(convertedPath, DEFAULT_VIEW_SUFFIX)) {
            convertedPath = convertedPath + DEFAULT_VIEW_SUFFIX;
        }

        this.path = convertedPath;
    }

    private void setResponseBody(final String path) throws IOException, URISyntaxException {
        if (startsWithRedirect(path)) {
            this.responseBody = new byte[0];
            return;
        }

        this.responseBody = FileIoUtils.loadFileFromClasspath(DEFAULT_VIEW_PREFIX + this.path);
    }

    private boolean startsWithRedirect(final String path) {
        return StringUtils.startsWith(path, REDIRECT);
    }
}
