package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;

import controller.SignUpController;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.*;

import static java.lang.Integer.parseInt;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String END_OF_LINE = "";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = convertStreamToHttpRequest(in);
            final String path = httpRequest.getRequestLine().getUrl().getPath();

            if (path.equals(SignUpController.path)) {
                SignUpController signUpController = new SignUpController();
                signUpController.run(httpRequest);
            }

            final byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getRequestLine().getUrl().getPath());

            final DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest convertStreamToHttpRequest(InputStream is) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = br.readLine();

        final RequestLine requestLine = RequestLine.parseFrom(line);
        logger.info(requestLine.toString());

        final RequestHeader requestHeader = new RequestHeader();
        while (! line.equals(END_OF_LINE) || line == null) {
            logger.info(line);
            line = br.readLine();
            requestHeader.add(line);
        }

        final RequestBody requestBody = RequestBody.parseFrom(
                IOUtils.readData(br, requestHeader.getContentLength())
        );
        logger.info(requestBody.toString());

        return new HttpRequest(requestLine, requestHeader, requestBody);
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
