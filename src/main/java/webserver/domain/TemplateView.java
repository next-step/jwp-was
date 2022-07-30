package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Template Engine을 사용하는 html 파일 정보 View 객체
 */
public class TemplateView extends DefaultView {
    private static final Logger logger = LoggerFactory.getLogger(TemplateView.class);

    private final Object attributes;

    public TemplateView(String prefix, String viewName, String suffix, Object attributes) {
        super(prefix, viewName, suffix);
        this.attributes = attributes;
    }

    public static TemplateView createDefaultHtmlView(String templateName, Object attributes) {
        return new TemplateView(PREFIX_TEMPLATE, templateName, SUFFIX_HTML, attributes);
    }

    public Object getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "TemplateView{" +
                "attributes=" + attributes +
                '}';
    }
}
