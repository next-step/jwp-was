package mvc.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateViewResolver implements ViewResolver {

    private Handlebars handlebars;

    public TemplateViewResolver() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    @Override
    public View resolveViewName(String viewName) throws IOException {
        return new TemplateView(handlebars.compile(viewName));
    }
}
