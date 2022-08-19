package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class TemplateView implements View {
    private static Handlebars HANDLEBARS;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        HANDLEBARS = new Handlebars(loader);
    }

    @Override
    public void render(HttpRequest request, HttpResponse response, String location) throws Exception {
        Template template = HANDLEBARS.compile(location);
        byte[] body = template.apply(request.getAttributes()).getBytes();
        response.responseBody(body);
    }
}
