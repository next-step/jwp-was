package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.HttpRequest;

import java.io.IOException;

public class TemplateUtils {
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";
    private static final String EMPTY_STRING = "";

    public static byte[] getTemplateData(final HttpRequest request) {
        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PREFIX);
        loader.setSuffix(TEMPLATE_SUFFIX);
        final Handlebars handlebars = new Handlebars(loader);
        try {
            return handlebars.compile(removeHTMLSuffix(request.getPath()))
                    .apply(request.getAttribute())
                    .getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private static String removeHTMLSuffix(final String view) {
        return view.replace(TEMPLATE_SUFFIX, EMPTY_STRING);
    }
}
