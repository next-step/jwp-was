package http.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TemplateRenderer {

    private static final String DEFAULT_PREFIX = "/templates";
    private static final String DEFAULT_SUFFIX = ".html";

    private final Map<String, Object> model = new HashMap<>();

    public void setModel(String key, Object value) {
        model.put(key, value);
    }

    public String render(String location) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(DEFAULT_PREFIX);
        loader.setSuffix(DEFAULT_SUFFIX);

        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (context, options) -> (Integer) context + 1);

        Template template = handlebars.compile(location);
        return template.apply(model);
    }
}
