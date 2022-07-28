package webserver.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;

public class HandleBarTemplateLoader {

    private static final String INCREASE = "inc";

    private HandleBarTemplateLoader() {
        throw new AssertionError();
    }

    public static String load(final String templateName, Object params) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper(INCREASE, (context, options) -> (int) context + 1);

        Template template = handlebars.compile(templateName);

        return template.apply(params);
    }
}
