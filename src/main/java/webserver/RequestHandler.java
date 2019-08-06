package webserver;

import model.http.RequestLine;
import model.http.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
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

        if (requestLine == null) {
            return Optional.empty();
        }

        UriPath path = requestLine.getRequestUri().getUriPath();

        Optional<byte[]> result = ResourceFinder.find(path);

        if (!result.isPresent()) {
            Optional<Method> method = ControllerFinder.findController(path);

            if (method.isPresent()) {
                Method methodFound = method.get();
                try {
                    result = ResourceFinder.find(UriPath.of(methodFound.invoke(methodFound.getDeclaringClass().newInstance()).toString() + ".html"));
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
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
