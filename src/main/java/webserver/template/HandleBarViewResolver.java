package webserver.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;

import java.io.IOException;
import java.util.Map;

public class HandleBarViewResolver implements ViewResolver {

    @Override
    public byte[] loadTemplate(String path, Map<String, Object> data) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        loader.setCharset(Charsets.UTF_8);

        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);
        String usersPage = template.apply(data);

        return usersPage.getBytes();
    }
}
