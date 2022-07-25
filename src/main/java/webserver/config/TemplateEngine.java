package webserver.config;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class TemplateEngine {

    private final Handlebars handlebars;

    public TemplateEngine(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    public static TemplateEngine getInstance() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return new TemplateEngine(new Handlebars(loader));
    }

    public String compile(String path, Object context) {
        try {
            var compile = handlebars.compile(path);

            return compile.apply(context);
        } catch (IOException e) {
            throw new IllegalArgumentException("템플릿 생성을 실패했습니다.", e);
        }
    }
}
