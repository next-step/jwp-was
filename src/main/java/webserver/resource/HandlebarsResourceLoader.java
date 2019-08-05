package webserver.resource;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.StringUtils;
import webserver.ModelAndView;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class HandlebarsResourceLoader extends AbstractResourceLoader {

    private static final List<String> AVAILABLE_RESOURCE = asList("html");

    @Override
    public boolean support(String name) {
        return AVAILABLE_RESOURCE.contains(StringUtils.endLastSplit(name, '.'));
    }

    @Override
    public String getResource(ModelAndView mav) throws IOException {
        String name = "templates" + mav.getViewName();
        validate(name);
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setSuffix("");
        Handlebars handlebars = new Handlebars(templateLoader);
        Template template = handlebars.compile(name);
        return template.apply(mav.getModel());
    }
}
