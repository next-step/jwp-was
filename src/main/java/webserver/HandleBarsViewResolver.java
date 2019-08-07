package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandleBarsViewResolver {

    private Handlebars handlebars;

    public HandleBarsViewResolver(String prefix, String suffix) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);

        this.handlebars = new Handlebars(loader);
    }

    public String render(String source, Object context) throws IOException {
        Template template = handlebars.compile(source);
        return template.apply(context);
    }
}
