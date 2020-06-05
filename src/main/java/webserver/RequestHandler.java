package webserver;

import http.request.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestLine requestLine;

    private static final String BASE_HTML_PATH = "./templates";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                  connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String request = br.readLine();
            log.debug("request: {}", request);

            this.requestLine = RequestLine.of(request);
            log.debug("path: {}", requestLine.getPath());
            log.debug("---------------------------------");

            //log.info("body: {}", this.requestLine.toString());

            while (!"".equals(request)) {
                request = br.readLine();
                log.debug("request: {}", request);
            }

            byte[] body = FileIoUtils.loadFileFromClasspath(BASE_HTML_PATH + this.requestLine.getPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
