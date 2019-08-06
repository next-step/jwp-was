package webserver.resolvers.view;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import webserver.handler.ModelView;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

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

    private void afterHandlebarProps(Handlebars handlebars) {
        handlebars.registerHelper("inc1", ((Helper<Integer>) (context, options) -> context.intValue() + 1));
    }

    public static HandlebarViewResolver of(String prefix, String sufix) {
        return new HandlebarViewResolver(prefix, sufix);
    }

    @Override
    public void resolve(HttpRequest httpRequest, HttpResponse httpResponse) {
        render(httpRequest, httpResponse);
    }
    
    private void render(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
        	
        	ModelView modelView = httpRequest.getModelView();
        	
        	Template template;
            try {
            	template = handlebars.compile(modelView.getView());
            } catch(IOException e) {
            	httpResponse.send404();
            	return; 
            }
            
            String appliedPage = template.apply(modelView.getModel());
            httpResponse.setHttpHeader(HttpHeaders.CONTENT_TYPE, "text/html");
            httpResponse.setResponseBody(appliedPage.getBytes());
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }
}
