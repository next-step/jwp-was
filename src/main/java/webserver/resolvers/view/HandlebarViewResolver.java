package webserver.resolvers.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.ModelView;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

public class HandlebarViewResolver implements ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(HandlebarViewResolver.class);

    private final Handlebars handlebars;

    private HandlebarViewResolver(String prefix, String sufix) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        this.handlebars = new Handlebars(loader);
        afterHandlebarProps(this.handlebars);
    }

    private void afterHandlebarProps(Handlebars handlebars){
        handlebars.registerHelper("inc1", new Helper<Integer>() {
            @Override
            public Object apply(Integer context, Options options) throws IOException {
                return context.intValue() + 1;
            }
        });
    }

    public static HandlebarViewResolver of(String prefix, String sufix){
        return new HandlebarViewResolver(prefix, sufix);
    }
    
    @Override
    public void resove(ModelView modelView, HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Template template = handlebars.compile(modelView.getView());

            String appliedPage = template.apply(modelView.getModel());
            httpResponse.setHttpHeader(HttpHeaders.CONTENT_TYPE, "text/html");
            httpResponse.setResponseBody(appliedPage.getBytes());

            logger.debug("template : {}", appliedPage);

        } catch (Exception e) {
            logger.error("{}", e);
        }
    }
}
