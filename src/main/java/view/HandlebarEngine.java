package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandlebarEngine implements View {
    private static final Logger logger = LoggerFactory.getLogger(Handlebars.class);

    private static final Handlebars HANDLEBARS;
    private static final Map<String, Template> TEMPLATES = new HashMap<>();

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        HANDLEBARS = new Handlebars(loader);
    }

    private Template loadTemplate(final String templateName) throws IOException {
        if (!TEMPLATES.containsKey(templateName)) {
            Template compiledTemplate = HANDLEBARS.compile(templateName);
            TEMPLATES.put(templateName, compiledTemplate);
        }

        return TEMPLATES.get(templateName);
    }

    @Override
    public void draw(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        try {
            Template template = loadTemplate(httpResponse.getForward());
            String body = template.apply(httpResponse.getModels());

            httpResponse.updateBody(body.getBytes());
        } catch (IOException e) {
            logger.error("Fail to write template : " + httpResponse.getForward());
            httpResponse.updateStatus(StatusCode.NOT_FOUND);
        }
    }
}
