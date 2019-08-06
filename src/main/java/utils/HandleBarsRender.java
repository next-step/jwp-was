package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import model.User;

public class HandleBarsRender {

  public static String render(String url) throws IOException {
    TemplateLoader loader = new ClassPathTemplateLoader();
    loader.setPrefix("/templates");
    loader.setSuffix(".html");
    Handlebars handlebars = new Handlebars(loader);

    Template template = handlebars.compile(url);

    Collection<User> users = DataBase.findAll();
    return template.apply(Collections.singletonMap("users", users));
  }
}
