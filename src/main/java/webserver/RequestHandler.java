package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import model.User;
import webserver.http.model.HttpRequest;
import webserver.http.model.RequestHeaders;
import webserver.http.model.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String line = bufferedReader.readLine();
            RequestLine requestLine = new RequestLine(line);

            StringBuilder stringBuilder = new StringBuilder();
            while (!"".equals(line)) {
                if (line == null) {
                    break;
                }
                line = bufferedReader.readLine();
                stringBuilder.append(line).append("\n");
            }
            RequestHeaders requestHeaders = new RequestHeaders(stringBuilder.toString());

            HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders);

            if (httpRequest.isStaticResource()) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.responsePath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else {
                User user = new User(httpRequest.getQueryStrings());
                logger.info("user: {}", user);
            }
        } catch (IOException | URISyntaxException e) {
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
