package webserver;

import http.RequestHeader;
import http.RequestLine;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.HttpServlet;
import servlet.ResourceViewRevolver;
import servlet.ServletMapping;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

  private Socket connection;

  public RequestHandler(Socket connectionSocket) {
    this.connection = connectionSocket;
  }

  public void run() {
    logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
        connection.getPort());

    try (InputStream in = connection.getInputStream(); OutputStream out = connection
        .getOutputStream()) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
      RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
      RequestHeader requestHeader = RequestHeader.parse(bufferedReader);
      HttpServlet servlet = ServletMapping.getServlet(requestLine);
      String view = servlet.service(requestLine);

      DataOutputStream dos = new DataOutputStream(out);

      byte[] body = ResourceViewRevolver.resolve(view);
      response200Header(dos, body.length);
      responseBody(dos, body);
    } catch (IOException e) {
      logger.error(e.getMessage());
    } catch (URISyntaxException e) {
      logger.error(e.getMessage());
    }
  }

  private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
    try {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      dos.writeBytes("\r\n");
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  private void responseBody(DataOutputStream dos, byte[] body) {
    try {
      dos.write(body, 0, body.length);
      dos.flush();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
