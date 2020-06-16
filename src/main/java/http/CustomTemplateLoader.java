package http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public class CustomTemplateLoader<T> {

    private static final String TEMPLATES_PATH = "/templates";

    private static final String HTML_EXTENSION = ".html";

    private final Handlebars handlebars;

    private final Template template;

    public CustomTemplateLoader(final String filePath) throws Exception {
        com.github.jknack.handlebars.io.TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PATH);
        loader.setSuffix(HTML_EXTENSION);
        this.handlebars = new Handlebars(loader);
        this.template = handlebars.compile(filePath);
    }

    public String applyTemplate(T object) throws Exception {
        return template.apply(object);
    }
}
