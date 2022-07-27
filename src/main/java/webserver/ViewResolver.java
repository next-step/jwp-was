package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.controller.PathMapping;
import webserver.response.HttpResponse;

import java.io.IOException;

public class ViewResolver {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";
    private String path;

    public ViewResolver(String path) {
        this.path = path;
    }

    public static String mapping(HttpResponse response, String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        if (!PathMapping.isExist(path)) {
            return handlebars.compile("/error/not_found").apply("");
        }

        Template template = handlebars.compile(path);

        String apply = template.apply(response.getModelMap());

        return apply;
    }
}
