package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseHeader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.of(inputStream);
            HttpResponse httpResponse = new HttpResponse();

            DispatcherServlet.INSTANCE.serve(httpRequest, httpResponse);

            writeResponse(dos, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            responseStatusLine(dos, httpResponse.statusLine());
            responseHeader(dos, httpResponse.getHeader());
            responseBody(dos, httpResponse.getBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getDefaultPath(String string) {
        return "defaultPath";
    }

    private void responseStatusLine(DataOutputStream dos, String statusLine) throws IOException {
        dos.writeBytes(statusLine + " \r\n");
        logger.debug(statusLine);
    }

    private void responseHeader(DataOutputStream dos, HttpResponseHeader responseHeader) throws IOException {
        for (String key : responseHeader.keySet()) {
            dos.writeBytes(key + ": " + responseHeader.getHeader(key) + "\r\n");
            logger.debug(key + ": " + responseHeader.getHeader(key));
        }
        dos.writeBytes("\r\n");
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
