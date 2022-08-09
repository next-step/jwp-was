package webserver.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@EqualsAndHashCode
public class HandlebarsObject {

    private Handlebars handlebars;

    public HandlebarsObject (TemplateLoader loader) {
        handlebars = new Handlebars(loader);
    }

    public static HandlebarsObject createHandlebarObject(String prefix, String suffix) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);

        return new HandlebarsObject(loader);
    }

    public String applyTemplate(String path , Object obj) throws IOException {
        Template template = this.getTemplate(path);
        return template.apply(obj);
    }

    private Template getTemplate(String path) throws IOException {
        return handlebars.compile(path);
    }
}
