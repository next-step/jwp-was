package webserver;

import model.http.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Optional;

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
            byte[] body = getBody(in).orElseThrow(() -> new FileNotFoundException("not found file"));
            response(out, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Optional<byte[]> getBody(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        int requestHeaderLineIndex = 0;
        RequestLine requestLine = null;

        while (!StringUtils.isEmpty(line = reader.readLine())) {
            if (++requestHeaderLineIndex == 1) {
                requestLine = RequestLine.of(line);
            }
        }

        return ResourceFinder.find(Objects.requireNonNull(requestLine).getRequestUri().getUriPath());
    }

    private void response(OutputStream out, byte[] body) {
        DataOutputStream dos = new DataOutputStream(out);
        response200Header(dos, body.length);
        responseBody(dos, body);
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
