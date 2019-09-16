package webserver.http.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequestLine;

import java.io.IOException;

public class ViewHandler {
    private static final Logger log = LoggerFactory.getLogger(ViewHandler.class);

    public static String render(HttpRequestLine requestLine, Object model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        String requestUri = requestLine.getRequestUri();
        requestUri = requestUri.substring(0, requestUri.lastIndexOf(".html"));

        Template template = handlebars.compile(requestUri);

        String view = template.apply(model);
        log.debug("list Page : {}", view);
        return view;
    }
}
