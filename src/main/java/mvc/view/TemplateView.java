package mvc.view;

import com.github.jknack.handlebars.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;
import was.http.HttpResponse;

public class TemplateView implements View {
    private static final Logger logger = LoggerFactory.getLogger(TemplateView.class);
    private Template template;

    public TemplateView(Template template) {
        this.template = template;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws Exception {
        logger.debug("attributes : {}", request.getAttributes());
        byte[] body = template.apply(request.getAttributes()).getBytes();
        response.addHeader("Content-Type", "text/html");
        response.responseBody(body);
    }
}
