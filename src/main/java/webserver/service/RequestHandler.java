package webserver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import webserver.header.request.RequestHeader;
import webserver.header.request.requestline.RequestLine;
import webserver.method.HttpMethod;

public class RequestHandler implements Runnable {
    private static final String NEXT_LINE = "\n";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String EMPTY = "";
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

            StringBuilder requestContents = new StringBuilder(line);

            while (isEnd(line)) {
                printlnLine(line);
                requestContents.append(NEXT_LINE);
                line = br.readLine();
                requestContents.append(line);
            }

            RequestHeader header = RequestHeader.create(requestContents.toString());
            RequestLine requestLine = header.requestLine();

            if (isGet(requestLine)) {
                ResponseGetHandler responseGetHandler = new ResponseGetHandler(connection);
                responseGetHandler.run(requestLine);
                return;
            }

            if (isPost(requestLine)) {
                ResponsePostHandler responsePostHandler = new ResponsePostHandler(connection);
                String requestBody = IOUtils.readData(br, header.contentLength());
                responsePostHandler.run(header, requestLine, requestBody);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printlnLine(String line) {
        System.out.println(line);
    }

    private boolean isEnd(String line) {
        return !EMPTY.equals(line);
    }

    private boolean isGet(RequestLine requestLine) {
        return HttpMethod.isGet(requestLine.httpMethod());
    }

    private boolean isPost(RequestLine requestLine) {
        return HttpMethod.isPost(requestLine.httpMethod());
    }
}