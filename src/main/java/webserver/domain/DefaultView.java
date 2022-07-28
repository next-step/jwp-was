package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * 기본 View 정보 객체
 */
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

    /**
     * 전달받은 이름으로 기본 설정에 맞춰 View 정보를 구성해 인스턴스를 생성 및 반환한다.
     *
     * @param viewName 뷰 이름
     * @return 뷰 정보 인스턴스
     */
    public static DefaultView createDefaultHtmlView(String viewName) {
        return new DefaultView(PREFIX_TEMPLATE, viewName, SUFFIX_HTML);
    }

    /**
     * 해당 뷰의 접두사를 반환한다.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 해당 뷰의 접미사를 반환한다.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 해당 뷰의 이름을 반환한다.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultView)) {
            return false;
        }
        DefaultView that = (DefaultView) o;
        return Objects.equals(prefix, that.prefix) && Objects.equals(suffix, that.suffix) && Objects.equals(viewName, that.viewName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, suffix, viewName);
    }
}
