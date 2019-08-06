package webserver.http.response.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.http.response.ContentType;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.io.IOException;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class TemplateResourceViewRenderer extends AbstractViewRenderer {

    private static final String TEMPLATE_RESOURCE_PATH_BASE = "/templates";
    private static final String PREFIX_TEMPLATE = ".html";
    private static Handlebars handlebars;
    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_RESOURCE_PATH_BASE);
        loader.setSuffix(PREFIX_TEMPLATE);
        handlebars = new Handlebars(loader);
    }

    private final ModelAndView modelAndView;

    public TemplateResourceViewRenderer(HttpResponse httpResponse, ModelAndView modelAndView) {
        super(httpResponse);
        this.modelAndView = modelAndView;
    }

    @Override
    public void render() {

        try {
            if (httpResponse.getRedirectUrl() != null) {
                httpResponse.setHttpStatus(HttpStatus.REDIRECT);
                httpResponse.addHeader("Location", httpResponse.getRedirectUrl());
                writeHeader(0);
            } else {
                httpResponse.setHttpStatus(HttpStatus.OK);
                byte[] body = compileTemplate(modelAndView).getBytes();
                httpResponse.setContentType(ContentType.HTML_UTF_8);
                writeHeader(body.length);
                writeBody(body);
            }
            flush();
        } catch (IOException e) {
            witeErrorPage(e);
        }
    }

    public String compileTemplate(ModelAndView modelAndView) throws IOException {
        Template template = handlebars.compile(modelAndView.getView());
        return template.apply(modelAndView.getModel());
    }
}
