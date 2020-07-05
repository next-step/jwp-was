package http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserListController extends UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        logger.info("UserListController - doGet");
        String cookie = request.getCookie();
        final String[] cookieTokens = cookie.split(";");
        if (cookieTokens.length > 1 && cookieTokens[1].trim().equals("logined=true")) {
            logger.info("=== logined user ===");
            try {
                Template template = getHandlebars()
                        .compile("user/list");
                String apply = template.apply(DataBase.findAll());
                response.setTemplate(apply);
                response.forward("/user/list.html");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.forward("/user/login.html");
        }
        logger.info("=== not logined user ===");
        super.doGet(request, response);
    }

    private Handlebars getHandlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        loader.setCharset(StandardCharsets.UTF_8);
        return new Handlebars(loader);
    }
}
