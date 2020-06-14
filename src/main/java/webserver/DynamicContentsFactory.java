package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DynamicContentsFactory {

    public static <T> byte[] createHTML(String location, T data) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(location);
        Map<String, T> parameterMap = new HashMap<>();
        parameterMap.put("data", data);
        String contents = template.apply(parameterMap);
        return contents.getBytes();
    }
}
