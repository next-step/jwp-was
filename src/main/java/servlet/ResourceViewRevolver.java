package servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class ResourceViewRevolver {

  private static final String NON_STATIC_RESOURCE_PATH_PREFIX = "./templates";
  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

  public static byte[] resolve(String path) throws IOException, URISyntaxException {
    if (isStaticResource(path)) {
      return FileIoUtils.loadFileFromClasspath(STATIC_RESOURCE_PATH_PREFIX + path);
    }
    return FileIoUtils.loadFileFromClasspath(NON_STATIC_RESOURCE_PATH_PREFIX + path);
  }

  private static boolean isStaticResource(String path) {
    if (path == null) {
      return false;
    }
    return path.contains(".css") || path.contains(".js");
  }
}
