package http.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TemplateView extends FileResourceView {

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
    protected BodyFile getBodyFile() throws IOException {
        Template template = handlebars.compile(path);
        byte[] body = template.apply(model.getModel()).getBytes();
        String fileName = new File(template.filename()).getName();
        return new BodyFile(fileName, body);
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
