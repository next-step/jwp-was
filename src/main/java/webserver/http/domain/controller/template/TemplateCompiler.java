package webserver.http.domain.controller.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.domain.request.Request;

import java.io.IOException;

public class TemplateCompiler {
    private final Handlebars handlebars;

    public TemplateCompiler(TemplateLoader templateLoader) {
        this.handlebars = new Handlebars(templateLoader);
    }

    public String compile(String path, Request request) {
        try {
            Template template = handlebars.compile(path);
            return template.apply(request.getAttributes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
