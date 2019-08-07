package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;

import java.io.IOException;
import java.util.Map;

public class HandlebarViewResolver implements ViewResolver {

    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    @Override
    public View render(String viewName) throws IOException {
        Template template = getTemplate(viewName);
        return new View(template.text());
    }

    @Override
    public View render(String viewName, Map<String, Object> data) throws IOException {
        Template template = getTemplate(viewName);
        return new View(template.apply(data));
    }


    private Template getTemplate(String viewName) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        loader.setCharset(Charsets.UTF_8);

        Handlebars handlebars = new Handlebars(loader);
        return handlebars.compile(viewName);
    }


}
