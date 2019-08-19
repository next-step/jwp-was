package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class HandleBarsViewResolver implements ViewResolver {

  private Handlebars handlebars;

  public HandleBarsViewResolver() {
    TemplateLoader loader = new ClassPathTemplateLoader();
    loader.setPrefix("/templates");
    loader.setSuffix(".html");
    handlebars = new Handlebars(loader);
  }

  @Override
  public View resolve(String viewName) {
    try {
      Template template = handlebars.compile(viewName);
      return new HandleBarsView(template);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
