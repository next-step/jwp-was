package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class HandlebarsUtils<T> {

    private static final Logger logger = LoggerFactory.getLogger(HandlebarsUtils.class);

    private static final String TEMPLATE_LOADER_PREFIX = "/templates";
    private static final String TEMPLATE_LOADER_SUFFIX = ".html";

    private final String filePath;
    private final String templateKey;
    private final Collection<T> models;

    public HandlebarsUtils(String filePath, String templateKey, Collection<T> models) {
        this.filePath = filePath;
        this.templateKey = templateKey;
        this.models = models;
    }

    public String apply() {
        TemplateLoader loader = new ClassPathTemplateLoader(TEMPLATE_LOADER_PREFIX, TEMPLATE_LOADER_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(filePath);

            return template.apply(Collections.singletonMap(templateKey, models));
        } catch (IOException e) {
            logger.error(String.format("template compile failed message: %s", e.getMessage()));
            throw new RuntimeException(e);
        }
    }
}
