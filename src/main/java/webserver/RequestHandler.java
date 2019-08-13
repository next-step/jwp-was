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
            RequestLine requestLine = getRequestLine(in).orElseThrow(() -> new FileNotFoundException("not found request line"));
            byte[] body = getBody(requestLine).orElseThrow(() -> new FileNotFoundException("not found file"));
            response(out, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Optional<byte[]> getBody(RequestLine requestLine) {
        UriPath path = requestLine.getRequestUri().getUriPath();

        Optional<byte[]> body;
        if ((body = ResourceFinder.find(path)).isPresent()) return body;

        return ControllerFinder.findController(path)
                .flatMap(method -> getBodyByControllerResource(requestLine, method));
    }

    private Optional<byte[]> getBodyByControllerResource(RequestLine requestLine, Method method) {
        try {
            String returnResourcePath = (String) ControllerMethodInvoker.invoke(method, requestLine.getRequestUri().getQuery());
            return ResourceFinder.find(UriPath.of(returnResourcePath + ".html"));
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            return Optional.empty();
        }
    }

    private Optional<RequestLine> getRequestLine(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        int requestHeaderLineIndex = 0;
        RequestLine requestLine = null;

        while (true) {
            try {
                if (StringUtils.isEmpty(line = reader.readLine())) break;
            } catch (IOException e) {
                return Optional.empty();
            }
            if (++requestHeaderLineIndex == 1) {
                requestLine = RequestLine.of(line);
            }
        }

        return Optional.ofNullable(requestLine);
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
