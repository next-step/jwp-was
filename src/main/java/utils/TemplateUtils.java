package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.util.Map;

public class TemplateUtils {
    private TemplateUtils() {}

    public static Handlebars createHandlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        return new Handlebars(loader);
    }

    public static String render(String templateLocation, Map<String, Object> parameterMap) throws IOException {
        return createHandlebars()
                .compile(templateLocation)
                .apply(parameterMap);
    }
}
