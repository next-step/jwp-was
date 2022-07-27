package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultView {
    private static final Logger logger = LoggerFactory.getLogger(DefaultView.class);
    public static final String SUFFIX_HTML = ".html";
    public static final String PREFIX_TEMPLATE = "./templates";
    public static final String STRING_EMPTY = "";
    private final String prefix;
    private final String suffix;
    private final String viewName;

    public DefaultView(String prefix, String viewName, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.viewName = viewName;
    }

    public static DefaultView createDefaultHtmlView(String viewName) {
        return new DefaultView(PREFIX_TEMPLATE, viewName, SUFFIX_HTML);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public String toString() {
        try {
            byte[] bytes = FileIoUtils.loadFileFromClasspath(prefix + viewName + suffix);

            return new String(bytes);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            return STRING_EMPTY;
        }
    }

}
