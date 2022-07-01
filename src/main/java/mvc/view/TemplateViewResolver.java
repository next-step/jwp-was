package mvc.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TemplateViewResolver implements ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(TemplateViewResolver.class);

    private final Handlebars handlebars;

    public TemplateViewResolver() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    @Override
    public View resolveViewName(String viewName) {
        logger.debug("viewName : {}", viewName);

        try {
            return new TemplateView(handlebars.compile(viewName));
        } catch (IOException e) {
            logger.error("Load Template fail: {}", e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
