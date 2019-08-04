package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.Handler;
import webserver.http.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private List<Handler> handlers;

    public RequestHandler(Socket connectionSocket, List<Handler> handlers) {
        this.connection = connectionSocket;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            HttpRequest httpRequest = HttpRequest.parse(br);

            handlers.stream()
                    .filter(handler -> handler.canHandle(httpRequest))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."))
                    .doHandle(httpRequest)
                    .write(new DataOutputStream(out));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
