package webserver.resolvers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.domain.TemplateView;
import webserver.exception.ViewResolveException;

import java.io.IOException;
import java.util.Objects;

public class TemplateViewResolver implements Resolver {
    @Override
    public boolean support(Object obj) {
        return Objects.nonNull(obj) && obj instanceof TemplateView;
    }

    @Override
    public String resolve(Object obj) {
        TemplateView view = (TemplateView) obj;
        try {
            TemplateLoader templateLoader = new ClassPathTemplateLoader();
            templateLoader.setPrefix(view.getPrefix());
            templateLoader.setSuffix(view.getSuffix());

            Handlebars handlebars = new Handlebars(templateLoader);

            Template template = handlebars.compile(getPullPath(view));

            return template.apply(view.getAttributes());
        } catch (IOException e) {
            throw new ViewResolveException(this.getClass(), e.getMessage());
        }
    }

    private String getPullPath(TemplateView view) {
        return view.getPrefix() + view.getViewName() + view.getSuffix();
    }
}
