package webserver.domain;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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


    @Override
    public String toString() {
        try {
            TemplateLoader templateLoader = new ClassPathTemplateLoader();
            templateLoader.setPrefix(getPrefix());
            templateLoader.setSuffix(getSuffix());

            Handlebars handlebars = new Handlebars(templateLoader);

            Template template = handlebars.compile(getPullPath());

            return template.apply(attributes);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return STRING_EMPTY;
        }
    }

    private String getPullPath() {
        return getPrefix() + getViewName() + getSuffix();
    }
}
