package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;

public class TemplateView extends AbstractView {

    private static final String TEMPLATE_PATH = "/templates";
    private static final String HTML_EXTENSION = ".html";

    private final Object data;
    private final String path;

    public TemplateView(String path, Object object) {
        this.data = object;
        this.path = path;
    }

    public byte[] read(HttpRequest request, HttpResponse response) {
        TemplateLoader loader = new ClassPathTemplateLoader(TEMPLATE_PATH, HTML_EXTENSION);
        loader.setCharset(Charset.defaultCharset());
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(path);
            return template.apply(data).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
