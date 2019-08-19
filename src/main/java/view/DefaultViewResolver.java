package view;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class DefaultViewResolver implements ViewResolver {

  private static final Logger logger = LoggerFactory.getLogger(DefaultViewResolver.class);
  private static final String DEFAULT_PATH = "./templates";

  @Override
  public View resolve(String viewName) {
    try {
      return new DefaultView(FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + viewName));
    } catch (IOException | URISyntaxException e) {
      logger.error("error : {}", e.getMessage());
    }
    return null;
  }
}
