package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.ContentType;
import webserver.domain.HttpHeaders;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatus;
import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.View;
import webserver.ui.FrontController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final FrontController frontController;

    public RequestHandler(Socket connectionSocket, FrontController frontController) {
        this.connection = connectionSocket;
        this.frontController = frontController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             OutputStream out = connection.getOutputStream();
             DataOutputStream dos = new DataOutputStream(out)) {

            HttpRequest httpRequest = HttpRequest.newInstance(br);

            HttpResponse httpResponse = requestHandle(httpRequest);
            responseHandle(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse requestHandle(HttpRequest httpRequest) {
        if (isResourceRequest(httpRequest.getRequestLine())) {
            return resourceHandle(httpRequest);
        }

        if (frontController.support(httpRequest.getRequestLine())) {
            return frontController.execute(httpRequest);
        }

        return new HttpResponse(HttpStatus.NOT_FOUND, null, HttpStatus.NOT_FOUND.value());
    }

    private boolean isResourceRequest(RequestLine requestLine) {
        Path path = requestLine.getPath();

        return ContentType.isResourceContent(path.getPathStr());
    }

    private HttpResponse resourceHandle(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Path path = requestLine.getPath();
        ContentType contentType = ContentType.suffixOf(path.getPathStr());

        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, new View(contentType.prefix(), path.getPathStr()), null);
        httpResponse.addHeader(HttpHeaders.CONTENT_TYPE, contentType.getContentType());

        return httpResponse;
    }

    private void responseHandle(DataOutputStream dos, HttpResponse httpResponse) {
        sendResponseHeader(dos, httpResponse);

        responseBody(dos, httpResponse);
    }

    private void sendResponseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.toStringHeader());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] body = httpResponse.getBodyOrView();

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
