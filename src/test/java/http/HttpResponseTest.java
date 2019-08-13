package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

class HttpResponseTest {

  @Test
  @DisplayName("redirect 시 response header 정보 확인")
  void redirectHeaderGet() throws FileNotFoundException {
    DataOutputStream dataOutputStream = new DataOutputStream(
        new FileOutputStream(new File("redirect.txt")));

    HttpResponse httpResponse = new HttpResponse(dataOutputStream);

    httpResponse.setHttpVersion("HTTP/1.1");
    httpResponse.setHttpStatus(HttpStatus.Found);
    httpResponse.setContentType("text/html;charset=UTF-8");
    httpResponse.setLocation("./templates/user/list.html");

    String httpHeaders = httpResponse.getHttpHeaders();

    assertThat(httpHeaders).isEqualTo(
        "HTTP/1.1 302 Found\r\nContent-Type: text/html;charset=UTF-8\r\nLocation: ./templates/user/list.html\r\n");
  }

  @Test
  @DisplayName("css 파일 response 정보 확인")
  void staticResourceHeaderGet() throws IOException, URISyntaxException {
    DataOutputStream dataOutputStream = new DataOutputStream(
        new FileOutputStream(new File("redirect.txt")));

    HttpResponse httpResponse = new HttpResponse(dataOutputStream);
    byte[] bytes = FileIoUtils.loadFileFromClasspath("./static/css/styles.css");
    httpResponse.setHttpVersion("HTTP/1.1");
    httpResponse.setHttpStatus(HttpStatus.OK);
    httpResponse.setContentType("text/css;charset=UTF-8");
    httpResponse.setContentLength(bytes.length);

    String httpHeaders = httpResponse.getHttpHeaders();

    assertThat(httpHeaders).isEqualTo(
        "HTTP/1.1 200 OK\r\nContent-Type: text/css;charset=UTF-8\r\nContent-Length: " + bytes.length
            + "\r\n");
  }
}