package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HandlebarLoadUtils {
    private static final Logger log = LoggerFactory.getLogger(HandlebarLoadUtils.class);

    public static Template getHandlebarTemplate(String path) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            return handlebars.compile("user/profile");
        } catch (IOException e) {
            log.error("load Handlebar Error : {}", e);
        }

        return null;
    }

}
