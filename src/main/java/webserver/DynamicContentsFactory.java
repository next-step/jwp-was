package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DynamicContentsFactory {

    private static final Map<String, Template> cachedTemplates = new HashMap<>();

    private static final Handlebars handlebars;

    static {
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix("/templates");
        templateLoader.setSuffix(".html");
        handlebars = new Handlebars(templateLoader);
    }

    public static <T> byte[] createHTML(String location, T data) throws IOException {
        Template template = getTemplate(location);

        Map<String, T> parameterMap = new HashMap<>();
        parameterMap.put("data", data);
        String contents = template.apply(parameterMap);
        return contents.getBytes();
    }

    private static Template getTemplate(String location) throws IOException {
        if (!cachedTemplates.containsKey(location)) {
            cachedTemplates.put(location, handlebars.compile(location));
        }
        return cachedTemplates.get(location);
    }
}