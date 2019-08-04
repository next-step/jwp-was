package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class ViewResolver {

    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private final Handlebars handlebars;

    {
        TemplateLoader loader = new ClassPathTemplateLoader();

        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);

        handlebars = new Handlebars(loader);
    }

    public String resolve(String path, Object data) throws IOException {
        return handlebars.compile(path).apply(data);
    }
}
