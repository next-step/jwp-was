package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;
import db.DataBase;
import model.User;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HandlebarViewResolver implements ViewResolver {

    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    @Override
    public byte[] render(String vieName) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        loader.setCharset(Charsets.UTF_8);

        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(vieName);

        Map<String, Object> data = new HashMap<>();
        Collection<User> users = DataBase.findAll();
        data.put("users", users);

        return template.apply(data).getBytes();
    }
}
