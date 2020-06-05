package web.servlet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import web.servlet.view.HandleBarsView;

public class HandleBarsViewResolver implements ViewResolver {

    private Handlebars handlebars;

    public HandleBarsViewResolver() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        this.handlebars = new Handlebars(loader);
    }

    @Override
    public View resolveViewName(String viewName) {
        return new HandleBarsView(viewName, handlebars);
    }
}
