package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Collection;

public class HandleBarCompiler {
    private static final String ROOT = "/templates";
    private static final String EXTENSION = ".html";
    private static final TemplateLoader TEMPLATE_LOADER = new ClassPathTemplateLoader();

    public static String compile(String location, Collection data) {
        try {
            TEMPLATE_LOADER.setPrefix(ROOT);
            TEMPLATE_LOADER.setSuffix(EXTENSION);
            Handlebars handlebars = new Handlebars(TEMPLATE_LOADER);

            Template template = handlebars.compile(location);

            return template.apply(data);
        } catch (IOException e) {

            return "";
        }
    }
}
