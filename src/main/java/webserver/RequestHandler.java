package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import model.HttpHeader;
import model.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

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

            DataOutputStream dos = new DataOutputStream(out);

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            final HttpHeader httpHeader = new HttpHeader(IOUtils.readHeaderData(br));
            final RequestLine requestLine = new RequestLine(httpHeader.getRequestLine());
            final String requestBody = IOUtils.readData(br, httpHeader.getContentLength());

            final HttpRequest httpRequest = new HttpRequest(requestLine, requestBody);


            if (requestLine.isCreateUserPath()) {
                final User user = User.createUser(httpRequest.getBody());
            }
            byte[] body = null;
            if (requestLine.getRequestPath() != null && !requestLine.getRequestPath().equals("")) {
                body = FileIoUtils.loadFileFromClasspath(requestLine.getRequestPath());
            }
            else {
                body = "Hello World".getBytes();
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
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
