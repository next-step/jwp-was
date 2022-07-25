package webserver.domain;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateEngineHelper{

    public static final String DEFAULT_PREFIX = "/templates";
    public static final String DEFAULT_SUFFIX = ".html";

    public static String applyTemplate(String path, Object o) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(DEFAULT_PREFIX);
        loader.setSuffix(DEFAULT_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(path);

        String appliedTemplate = template.apply(o);

        return appliedTemplate;
    }
}
