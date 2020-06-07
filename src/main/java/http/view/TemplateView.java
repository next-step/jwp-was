package http.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.Headers;
import http.HttpStatus;
import java.io.IOException;
import java.util.Objects;

public class TemplateView implements View {

    private final static Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    private final String path;
    private final TemplateModel model;

    public TemplateView(String path, TemplateModel model) {
        this.path = path;
        this.model = model;
    }

    public TemplateView(String path) {
        this(path, new TemplateModel());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus._200;
    }

    @Override
    public byte[] getBody() throws IOException {
        return handlebars.compile(path).apply(model.getModel()).getBytes();
    }

    @Override
    public Headers getHeaders() {
        Headers headers = new Headers();
        headers.add("Content-Type", "text/html;charset=utf-8");
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateView that = (TemplateView) o;
        return Objects.equals(path, that.path) &&
            Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, model);
    }
}
