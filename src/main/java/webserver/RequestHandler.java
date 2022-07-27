package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.ContentType;
import webserver.domain.DefaultView;
import webserver.domain.HttpHeaders;
import webserver.domain.HttpRequest;
import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.ResponseEntity;
import webserver.handlers.ControllerContainer;
import webserver.ui.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ControllerContainer controllerContainer;

    public RequestHandler(Socket connectionSocket, ControllerContainer controllerContainer) {
        this.connection = connectionSocket;
        this.controllerContainer = controllerContainer;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             OutputStream out = connection.getOutputStream();
             BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out))) {

            HttpRequest httpRequest = HttpRequest.newInstance(br);
            logger.debug("Http Request: {}", httpRequest);

            ResponseEntity<?> responseEntity = requestHandle(httpRequest);
            responseHandle(wr, responseEntity);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private ResponseEntity<?> requestHandle(HttpRequest httpRequest) {
        if (isResourceRequest(httpRequest.getRequestLine())) {
            return resourceHandle(httpRequest);
        }

        if (controllerContainer.support(httpRequest.getRequestLine())) {
            Controller controller = controllerContainer.findController(httpRequest.getRequestLine());

            return controller.execute(httpRequest);
        }

        return ResponseEntity.notFound().build();
    }

    private boolean isResourceRequest(RequestLine requestLine) {
        Path path = requestLine.getPath();

        return ContentType.isResourceContent(path.getPathStr());
    }

    private ResponseEntity<DefaultView> resourceHandle(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Path path = requestLine.getPath();
        ContentType contentType = ContentType.suffixOf(path.getPathStr());

        return ResponseEntity.ok()
                .headers(HttpHeaders.CONTENT_TYPE, contentType.getContentType())
                .body(new DefaultView(contentType.prefix(), path.getPathStr(), DefaultView.STRING_EMPTY));
    }

    private void responseHandle(BufferedWriter dos, ResponseEntity<?> responseEntity) throws IOException {
        logger.debug("Response Header: {}", responseEntity.getHeaders());

        dos.write(responseEntity.toString());
        dos.flush();
    }
}
