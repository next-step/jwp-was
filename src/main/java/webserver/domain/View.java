package webserver.domain;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class View {
    private final String viewName;
    private final byte[] content;

    public View(String prefix, String viewName) {
        this.viewName = viewName;
        this.content = getViewContent(prefix, viewName);
    }

    private byte[] getViewContent(String prefix, String viewName) {
        try {
            return FileIoUtils.loadFileFromClasspath(prefix + viewName);
        } catch (IOException | URISyntaxException e) {
            return new byte[0];
        }
    }

    public String getViewName() {
        return viewName;
    }

    public byte[] getContent() {
        return content;
    }
}
