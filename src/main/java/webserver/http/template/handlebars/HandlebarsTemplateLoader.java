package webserver.http.template.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarsTemplateLoader {
    private final TemplateLoader loader;
    private final Handlebars handlebars;

    public HandlebarsTemplateLoader() {
        this.loader = new ClassPathTemplateLoader();
        this.loader.setPrefix("/templates");
        this.loader.setSuffix(".html");

        this.handlebars = new Handlebars(loader);
        this.registerCustomHelper();
    }

    private void registerCustomHelper() {
        this.handlebars.registerHelper("inc", (context, options) -> (int) context + 1);
    }

    public String load(final String location, final Object context) throws IOException {
        Template template = this.handlebars.compile(location);
        return template.apply(context);
    }
}
