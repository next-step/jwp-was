package template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HandleBars {
    private static final Logger logger = LoggerFactory.getLogger(HandleBars.class);

    public static String getHTML(String templateFilePath, Users users) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelpers(new HandleBarHelper());

        Template template = handlebars.compile(templateFilePath);

        String profilePage = template.apply(users);
        logger.debug("ProfilePage : {}", profilePage);

        return profilePage;
    }
}
