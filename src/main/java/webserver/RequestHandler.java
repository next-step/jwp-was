package webserver;

import model.http.HttpRequest;
import model.http.RequestLine;
import model.http.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            getResponseBody(httpRequest, out);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private boolean getResponseBody(HttpRequest httpRequest, OutputStream out) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        RequestLine requestLine = httpRequest.getHttpRequestHeader().getRequestLine();
        UriPath uriPath = requestLine.getRequestUri().getUriPath();

        Optional<byte[]> body;
        if ((body = ResourceFinder.find(uriPath)).isPresent()) {
            response(out, body.get());
            return true;
        }

        Optional<Method> method = ControllerFinder.findController(requestLine.getMethod(), uriPath);

        if (method.isPresent()) {
            String returnResourcePath = returnResourcePath(httpRequest, method.get());

            if (isRedirect(returnResourcePath)) {
                redirectResponse(out, returnResourcePath.replace("redirect:", ""));
                return true;
            }
            body = getBodyByControllerResource(returnResourcePath);
            response(out, body.get());
        }
        return true;
    }

    private Optional<byte[]> getBodyByControllerResource(String returnResourcePath) {
        return ResourceFinder.find(UriPath.of(returnResourcePath + ".html"));
    }

    private boolean isRedirect(String returnValueByControllerMethod) {
        return returnValueByControllerMethod.startsWith("redirect:");
    }

    private String returnResourcePath(HttpRequest httpRequest, Method method) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return (String) ControllerMethodInvoker.invoke(method, httpRequest);
    }

    private boolean response(OutputStream out, byte[] body) {
        DataOutputStream dos = new DataOutputStream(out);
        return response200Header(dos, body.length)
                && responseBody(dos, body);
    }

    private boolean redirectResponse(OutputStream out, String location) {
        DataOutputStream dos = new DataOutputStream(out);
        return response302Header(dos, location);
    }

    private boolean response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        boolean result = false;
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
            result = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    private boolean response302Header(DataOutputStream dos, String location) {
        boolean result = false;
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + " \r\n");
            dos.writeBytes("\r\n");
            result = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    private boolean responseBody(DataOutputStream dos, byte[] body) {
        boolean result = false;
        try {
            dos.write(body, 0, body.length);
            dos.flush();
            result = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return result;
    }
}
