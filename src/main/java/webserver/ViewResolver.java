package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

public class ViewResolver {
    private static final String TEMPLATE_FILE_PREFIX = "/templates";
    private static final String HTML_FILE_SUFFIX = ".html";
    private static final String ERROR_TEMPLATES_PREFIX = "error";

    public static String mapping(HttpRequest request, HttpResponse response) throws IOException {
        String path = Router.route(request, response).orElse("");
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_FILE_PREFIX);
        loader.setSuffix(HTML_FILE_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);

        if (path.startsWith("/" + ERROR_TEMPLATES_PREFIX) || path.startsWith(ERROR_TEMPLATES_PREFIX))
            return template.text();

        return template.apply(response.getModel().getModelMap());
    }
}
