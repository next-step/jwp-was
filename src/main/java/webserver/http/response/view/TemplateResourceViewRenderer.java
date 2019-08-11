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
    private static final String TEMPLATE_EXTENSION = ".html";
    private static Handlebars handlebars;

    private final String prefix;

    public TemplateResourceViewRenderer(String prefix) {
        this.prefix = prefix;
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_RESOURCE_PATH_BASE);
        loader.setSuffix(TEMPLATE_EXTENSION);
        handlebars = new Handlebars(loader);
    }

    @Override
    protected byte[] createResponseInfo(ModelAndView modelAndView, HttpResponse httpResponse) {
        byte[] template = EMPTY_BODY;
        try {
            httpResponse.setContentType(ContentType.HTML_UTF_8);
            httpResponse.setHttpStatus(HttpStatus.OK);
            template = compileTemplate(modelAndView).getBytes();
        } catch (IOException e) {
            writeErrorPage(e, httpResponse);
        }
        return template;
    }

    private String compileTemplate(ModelAndView modelAndView) throws IOException {
        Template template = handlebars.compile(modelAndView.getOriginalViewName(prefix));
        return template.apply(modelAndView.getModel());
    }
}
