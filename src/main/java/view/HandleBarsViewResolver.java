package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleBarsViewResolver implements ViewResolver {

  private static final Logger logger = LoggerFactory.getLogger(HandleBarsViewResolver.class);
  private Handlebars handlebars;
  private Map params;

  public HandleBarsViewResolver() {
    this(null);
  }

  public <V, K> HandleBarsViewResolver(Map<K, V> params) {
    if (params != null) {
      this.params = params;
    }
    TemplateLoader loader = new ClassPathTemplateLoader();
    loader.setPrefix("/templates");
    loader.setSuffix(".html");
    handlebars = new Handlebars(loader);

  }

  @Override
  public View resolve(String viewName) {
    try {
      Template template = handlebars.compile(viewName);
      return new HandleBarsView(template, params);
    } catch (IOException e) {
      logger.error("error : {}",e.getMessage());
    }
    return null;
  }
}
