package view;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class DefaultViewResolver implements ViewResolver {

  private static final String DEFAULT_PATH = "./templates";

  @Override
  public View resolve(String viewName) {
    try {
      return new DefaultView(FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + viewName));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
}
