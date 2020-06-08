package http.view;

import com.github.jknack.handlebars.Template;
import http.requests.HttpRequest;
import http.responses.HttpResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * Handlerbars 템플릿 뷰
 */
public class TemplateView implements View {

    private final Template template;

    public TemplateView(Template template) {
        this.template = template;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException {
        final Optional<Template> viewToRender = Optional.ofNullable(template);
        if (viewToRender.isPresent()) {
            final String renderedBody = template.apply(response.getAttribute());
            response.responseBody(renderedBody.getBytes());
            return;
        }
        response.responseNotFound();
    }
}
