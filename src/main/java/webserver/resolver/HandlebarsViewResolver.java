package webserver.resolver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import webserver.http.ContentType;
import webserver.http.ModelAndView;

import java.io.IOException;

public class HandlebarsViewResolver implements ViewResolver {

    private static final String DEFAULT_PREFIX = "/templates";
    private static final String DEFAULT_SUFFIX = ".html";

    private Handlebars handlebars;

    public HandlebarsViewResolver() {
        handlebars = new Handlebars(new ClassPathTemplateLoader(DEFAULT_PREFIX, DEFAULT_SUFFIX))
                .registerHelper("increase", (Helper<Integer>) (index, options) -> index + 1);
    }

    @Override
    public byte[] resolve(String viewName) throws Exception {
        String body = compileView(viewName).text();
        return body.getBytes();
    }

    @Override
    public byte[] resolve(ModelAndView modelAndView) throws Exception {
        String body = compileView(modelAndView.getViewName())
                .apply(modelAndView.getModel());
        return body.getBytes();
    }

    private Template compileView(String viewName) throws IOException {
        return handlebars.compile(removeSuffix(viewName));
    }

    private String removeSuffix(String viewName) {
        if (viewName.endsWith(DEFAULT_SUFFIX)) {
            int endIndex = viewName.lastIndexOf(DEFAULT_SUFFIX);
            return viewName.substring(0, endIndex);
        }
        return viewName;
    }

    @Override
    public String getContentType() {
        return ContentType.TEXT_HTML_UTF_8.getValue();
    }
}
