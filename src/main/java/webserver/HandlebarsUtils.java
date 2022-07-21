package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarsUtils {

    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";
    private static final Handlebars HANDLEBARS;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader(PREFIX, SUFFIX);
        HANDLEBARS = new Handlebars(loader);
    }

    private HandlebarsUtils() {
    }

    public static String getView(String viewName, Object model) {
        try {
            Template template = HANDLEBARS.compile(viewName);
            return template.apply(model);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
