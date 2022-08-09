package webserver;

import controller.DispatchController;
import exception.ResourceNotFoundException;
import model.HttpHeaders;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final DispatchController dispatchController = new DispatchController();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            HttpRequest request = HttpRequest.of(bufferedReader);
            HttpResponse httpResponse = handle(request);
            writeHttpResponse(httpResponse, dos);
        } catch (IOException | URISyntaxException | ResourceNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse handle(HttpRequest request) throws IOException, URISyntaxException {
        return dispatchController.handleRequest(request);
    }

    private void writeHttpResponse(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        writeHttpHeaders(httpResponse, dos);
        dos.writeBytes("\r\n");
        writeBody(dos, httpResponse.getBody());
    }

    private void writeHttpHeaders(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("HTTP/1.1 %s \r\n", httpResponse.getHttpResponseCode()));
        for (Map.Entry<String, Object> header : httpResponse.getHeaders()) {
            dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
        }

        if (httpResponse.containsCookie()) {
            dos.writeBytes(String.format("%s: %s \r\n", HttpHeaders.SET_COOKIE, httpResponse.getCookie()));
        }
    }

    private void writeBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
