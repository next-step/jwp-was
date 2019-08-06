package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

/**
 * Created by hspark on 2019-08-05.
 */
public class HandlebarsLoader {
    public String load(String path, Map<String, Object> param) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);


        try {
            Template template = handlebars.compile(path);
            return template.apply(param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
