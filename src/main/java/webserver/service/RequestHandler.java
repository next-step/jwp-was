package webserver.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import webserver.request.header.RequestHeader;
import webserver.method.HttpMethod;
import webserver.response.get.GetIndexHtmlResponse;

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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();

            StringBuilder requestContents = new StringBuilder(line);
            DataOutputStream dos = new DataOutputStream(out);

            while (isEnd(line)) {
                printlnLine(line);
                requestContents.append(NEXT_LINE);
                line = br.readLine();
                requestContents.append(line);
            }

            RequestHeader header = RequestHeader.create(requestContents.toString());

            if (isGet(header)) {
                ResponseGetHandler responseGetHandler = new ResponseGetHandler();
                GetIndexHtmlResponse response = new GetIndexHtmlResponse();
                byte[] body = response.response(header.index());
                writeAndFlush(responseGetHandler.handle(header, body.length), body, dos);
                return;
            }

            if (isPost(header)) {
                ResponsePostHandler responsePostHandler = new ResponsePostHandler();
                String requestBody = IOUtils.readData(br, header.contentLength());
                writeAndFlush(responsePostHandler.handle(header, requestBody), EMPTY.getBytes(), dos);
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

    private boolean isGet(RequestHeader requestLine) {
        return HttpMethod.isGet(requestLine.httpMethod());
    }

    private boolean isPost(RequestHeader requestLine) {
        return HttpMethod.isPost(requestLine.httpMethod());
    }

    private static void writeAndFlush(String header, byte[] body, DataOutputStream dos) {
        try {
            dos.writeBytes(header);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
