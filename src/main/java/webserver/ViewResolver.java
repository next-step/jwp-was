package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.ModelAndView;

import java.io.IOException;

public class ViewResolver {
    private static final String TEMPLATE_FILE_PREFIX = "/templates";
    private static final String HTML_FILE_SUFFIX = ".html";
    private static final String ERROR_TEMPLATES_PREFIX = "error";

    public static String mapping(ModelAndView modelAndView) throws IOException {
        String view = modelAndView.getView();
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_FILE_PREFIX);
        loader.setSuffix(HTML_FILE_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(view);

        if (isErrorPage(view))
            return template.text();

        return template.apply(modelAndView.getModelMap());
    }

    private static boolean isErrorPage(String view) {
        return view.startsWith("/" + ERROR_TEMPLATES_PREFIX) || view.startsWith(ERROR_TEMPLATES_PREFIX);
    }
}
