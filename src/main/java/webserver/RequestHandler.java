package webserver;

import actions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.*;
import webserver.http.response.HttpStatus;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;

        ResourceManager.addResource("text/css", Constants.STATIC_PATH);
        ResourceManager.addResource("font/woff", Constants.STATIC_PATH);
        ResourceManager.addResource("font/ttf", Constants.STATIC_PATH);
        ResourceManager.addResource("text/javascript", Constants.STATIC_PATH);
        ResourceManager.addResource("text/html", Constants.TEMPLATES_PATH);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = null;

            try {

                Action action = ActionFactory.getInstance(httpRequest.getMethod(), httpRequest.getPath());

                if (action != null) {
                    httpResponse = action.execute(httpRequest);
                }

                if (httpResponse == null) {
                    String accept = httpRequest.getHeader("Accept");
                    byte[] resource = ResourceManager.get(accept, httpRequest.getPath());

                    httpResponse = new HttpResponse(HttpStatus.OK, ResourceManager.matchContentType(accept), resource);
                }

            } catch (FileNotFoundException e) {
                httpResponse = new HttpResponse(HttpStatus.NOT_FOUND);

            } catch (AuthenticationException e) {
                httpResponse = new HttpResponse(HttpStatus.OK, Constants.TEXT_HTML, ResourceManager.get(Constants.TEXT_HTML, Constants.HELLO_PAGE));
            }

            httpResponse.sendResponse(out);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
