package webserver.http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HandlebarCompiler {
    private HandlebarCompiler() {
        throw new AssertionError();
    }

    public static byte[] compile(String path, Object content) {
        try {
            Template template = compileTemplate(path);
            final String html = template.apply(content);
            return html.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Template compileTemplate(String path) {
        Template template;
        try {
            template = getHandlebars().compile(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return template;
    }

    private static Handlebars getHandlebars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return new Handlebars(loader);
    }
}
