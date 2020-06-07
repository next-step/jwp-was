package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.HttpRequest;
import http.request.mapper.StaticResourceMapper;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.io.IOException;

@Slf4j
public class StaticResourceController extends AbstractController {
    private final String prefix;
    private final String suffix;
    private final String location;

    public StaticResourceController(StaticResourceMapper mapper) {
        this(mapper.getPrefix(), mapper.getSuffix(), mapper.getPath());
    }

    public StaticResourceController(String prefix, String suffix, String location) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.location = location;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String staticResource = getStaticResource(prefix, suffix, location);
    }

    private String getStaticResource(String prefix, String suffix, String location) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(location);

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String staticResource = template.apply(user);
        log.debug("staticResource : {}", staticResource);

        return staticResource;
    }
}
