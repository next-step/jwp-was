package servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.StaticViewRevolver;

public class StaticViewRevolverTest {

  @Test
  @DisplayName("static파일(css , js) 파일을 제외한 리소스 탐색시에 ./templates 하위에서 찾도록 한다 ")
  void nonStaticFileViewResolver() {
    String path = "/index.html";
    try {
      byte[] body = StaticViewRevolver.resolve(path);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("static파일(css , js) 탐색시에 ./static 하위에서 찾도록 한다 ")
  void staticFileViewResolver() {
    String path = "/js/scripts.js";
    try {
      byte[] body = StaticViewRevolver.resolve(path);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
