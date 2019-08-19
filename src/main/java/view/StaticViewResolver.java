package view;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class StaticViewResolver implements ViewResolver {

  private static final Logger logger = LoggerFactory.getLogger(StaticViewResolver.class);


  private static final String DEFAULT_PATH = "./static";

  @Override
  public View resolve(String viewName) {
    try {
      return new StaticView(FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + viewName));
    } catch (IOException | URISyntaxException e) {
      logger.error("error : {}", e.getMessage());
    }
    return null;
  }
}
