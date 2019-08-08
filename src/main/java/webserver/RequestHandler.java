package webserver;

import http.HttpRequest;
import http.HttpResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.HttpServlet;
import servlet.ServletMapper;

public class RequestHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

  private Socket connection;
  private ServletMapper servletMapper;

  public RequestHandler(Socket connectionSocket, ServletMapper servletMapper) {
    this.connection = connectionSocket;
    this.servletMapper = servletMapper;
  }

  public void run() {
    logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
        connection.getPort());

    try (InputStream in = connection.getInputStream(); OutputStream out = connection
        .getOutputStream()) {

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
      HttpRequest httpRequest = new HttpRequest(bufferedReader);
      HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

      HttpServlet servlet = servletMapper.getServlet(httpRequest.getPath());
      servlet.service(httpRequest, httpResponse);

    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }


}
