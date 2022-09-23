package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.response.HttpResponse;
import webserver.servlet.RequestMappingHandler;
import webserver.servlet.ServletConfig;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.parse(in);

            DataOutputStream dos = new DataOutputStream(out);

            if (httpRequest.isStaticResource()) {
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getRequestLine().getPathValue());
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }

            RequestMappingHandler requestMappingHandler = new RequestMappingHandler(ServletConfig.servlets());
            HttpResponse httpResponse = requestMappingHandler.doService(httpRequest);

            String redirectFile = httpResponse.getRedirectFile();
            byte[] body = FileIoUtils.loadFileFromClasspath(redirectFile);
            response302Header(dos, httpResponse, body.length);
            responseBody(dos, body);
        } catch (IOException |
                 URISyntaxException e) {
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

    private void response302Header(DataOutputStream dos, HttpResponse httpResponse, int lengthOfBodyContent) {
        try {
            dos.writeBytes(httpResponse.getResponseLine() + "\r\n");
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
