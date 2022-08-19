package mvc.view;

import com.github.jknack.handlebars.Template;
import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class TemplateView implements View {
    private static final Logger logger = LoggerFactory.getLogger(TemplateView.class);
    private Template template;

    public TemplateView(Template template) {
        this.template = template;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException {
        logger.debug("attributes : {}", request.getBodyAttributes());
        byte[] body = template.apply(request.getBodyAttributes()).getBytes(StandardCharsets.UTF_8);
        response.responseOk(
                HttpHeader.from(Collections.singletonMap(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8")),
                body);
    }
}
