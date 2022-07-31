package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public class ViewResolver {

    private final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html"));

    public View resolveView(String view) {
        if (view.startsWith("redirect:")) {
            String targetUrl = view.replace("redirect:", "");
            return new RedirectView(targetUrl);
        }

        return new HandleBarView(view, handlebars);
    }

}
