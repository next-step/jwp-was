package http.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * {@link utils.TemplateRenderer#render(String, Object) 부분 이동}
 */
public class TemplateViewResolver implements ViewResolver {

    private static final Logger log = LoggerFactory.getLogger(TemplateViewResolver.class);

    private final Handlebars handlebars;

    public TemplateViewResolver() {
        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        this.handlebars = new Handlebars(loader);
    }

    @Override
    public View resolve(String viewName) {
        try {
            final Template template = handlebars.compile(removeSuffix(viewName));
            return new TemplateView(template);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new TemplateView(null);
        }
    }

    private String removeSuffix(String viewName) {
        if (viewName.endsWith(".html")) {
            return viewName.replace(".html", "");
        }
        return viewName;
    }
}
