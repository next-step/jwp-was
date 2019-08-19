package view;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class StaticViewResolver implements ViewResolver {

  private static final String DEFAULT_PATH = "./static";

  @Override
  public View resolve(String viewName) {
    try {
      return new StaticView(FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + viewName));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }
}
