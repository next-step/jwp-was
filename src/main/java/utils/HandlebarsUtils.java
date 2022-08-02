package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.util.List;

public class HandlebarsUtils {

    private static final String TEMPLATES_PATH = "/templates";
    private static final String HTML_EXTENSION = ".html";
    private static final String ERROR_TEMPLATE = "ERROR";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static Handlebars getHandlebars() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PATH);
        loader.setSuffix(HTML_EXTENSION);
        return new Handlebars(loader);
    }

    public static String getUserListTemplate(String templatePath, List<User> users) {
        try {
            Handlebars handlebars = getHandlebars();
            Template template = handlebars.compile(templatePath);
            return template.apply(users);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return ERROR_TEMPLATE;
    }
}
