package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TemplateView {
    private static final Logger log = LoggerFactory.getLogger(TemplateView.class);
    private final Handlebars handlebars;

    public TemplateView() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        this.handlebars = new Handlebars(loader);
        this.handlebars.registerHelper("increaseOne", (Helper<Integer>) (context, options) -> context + 1);
    }

    public String read(String filePath, Object context) {
        try {
            Template template = handlebars.compile(filePath);
            return template.apply(context);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return "";
    }
}
