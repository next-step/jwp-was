package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class ViewResolver {

    public View resolveView(String view) {
        if (view.startsWith("redirect:")) {
            String targetUrl = view.replace("redirect:", "");
            return new RedirectView(targetUrl);
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        return new HandleBarView(view, handlebars);
    }

}
