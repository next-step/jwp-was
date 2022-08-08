package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.Map;

public class HandlebarsUtils {

    public static final String USER_LIST_PATH = "user/list";
    public static final String USERS_KEY = "users";
    public static final String PREFIX = "/templates";
    public static final String SUFFIX = ".html";

    public static String makeUserListTemplate() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(USER_LIST_PATH);

        return template.apply(Map.of(USERS_KEY, DataBase.findAll()));
    }
}
