package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.stream.Stream;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            RequestLine requestLine = RequestLine.parse(line);
            logger.debug("request line : {}", requestLine);

            String path = requestLine.getPath().getPath();
            logger.debug("request path : {}", path);

            String method = requestLine.getMethod();
            logger.debug("request method : {}", method);

            HttpHeader httpHeader = new HttpHeader();
            while (!"".equals(line)) {
                line = br.readLine();
                logger.debug("header : {}", line);
                httpHeader.addHeader(line);
            }


            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World22".getBytes();

            if (path.endsWith(".html")) {
                body = FileIoUtils.loadFileFromClasspath("./templates"+path);
            }

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
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
