package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class HttpResponse {

  private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
  private static final String NON_STATIC_RESOURCE_PATH_PREFIX = "./templates";
  private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";
  private DataOutputStream dos;

  public HttpResponse(DataOutputStream dos) {
    this.dos = dos;
  }

  private void response200Header(int lengthOfBodyContent) {
    try {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void sendRedirect(String redirectUrl) {
    try {
      dos.writeBytes("HTTP/1.1 302 Found \r\n");
      dos.writeBytes("Location: " + redirectUrl);
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }


  private void responseBody(byte[] body) {
    try {
      dos.write(body, 0, body.length);
      dos.flush();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void forward(String url) {
    try {
      byte[] body = FileIoUtils.loadFileFromClasspath(NON_STATIC_RESOURCE_PATH_PREFIX + url);
      response200Header(body.length);
      responseBody(body);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

  }
}
