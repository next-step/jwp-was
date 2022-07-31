package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public class ViewResolver {

    private static final String REDIRECT_PREFIX = "redirect:";

    private final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html"));

    public View resolveView(String view) {
        if (view.startsWith(REDIRECT_PREFIX)) {
            String targetUrl = view.substring(REDIRECT_PREFIX.length());
            return new RedirectView(targetUrl);
        }

        return new HandleBarView(view, handlebars);
    }

}
