package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TemplateRenderer {

    private static final Logger log = LoggerFactory.getLogger(TemplateRenderer.class);

    public static String render(String path, Object model) {
        try {
            final TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            final Handlebars handlebars = new Handlebars(loader);

            final Template template = handlebars.compile(path);
            return template.apply(model);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
}
