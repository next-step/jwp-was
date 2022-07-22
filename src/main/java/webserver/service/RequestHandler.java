package webserver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.HttpMethod;
import webserver.domain.RequestLine;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            RequestLine requestLine = RequestLine.create(line);
            while (isEnd(line)) {
                printlnLine(line);
                line = br.readLine();
            }

            if (isGet(requestLine)) {
                ResponseGetHandler responseGetHandler = new ResponseGetHandler(connection);
                responseGetHandler.run(requestLine);
                return;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printlnLine(String line) {
        System.out.println(line);
    }

    private boolean isEnd(String line) {
        return !"".equals(line);
    }

    private boolean isGet(RequestLine requestLine) {
        return HttpMethod.isGet(requestLine.httpMethod());
    }

    private boolean isPost(RequestLine requestLine) {
        return HttpMethod.isPost(requestLine.httpMethod());
    }
}
