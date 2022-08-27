package webserver;

import http.HttpSessionInterceptor;
import http.httprequest.HttpRequest;
import http.httpresponse.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connection
    ) {
        this.connection = connection;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {

            HttpRequest httpRequest = HttpRequest.from(br);
            Optional<Controller> controller = RequestMapper.getController(httpRequest);

            HttpResponse httpResponse = getHttpResponseSafety(httpRequest, controller);
            writeResponse(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse getHttpResponseSafety(HttpRequest httpRequest, Optional<Controller> controller) {

        if (controller.isEmpty()) {
            return HttpResponse.notFound();
        }

        try {
            return new HttpSessionInterceptor(controller.get()).execute(httpRequest);
        } catch (IOException | URISyntaxException e) {
            return HttpResponse.internalServerError();
        }
    }

    private void writeResponse(DataOutputStream dos,
                               HttpResponse httpResponse) throws IOException {
        dos.writeBytes(String.format("HTTP/1.1 %s \r%n", httpResponse.getHttpStatusCode()));
        writeHeaders(dos, httpResponse);
        dos.writeBytes("\r\n");
        dos.write(httpResponse.getBody(), 0, httpResponse.getContentLength());
        dos.flush();
    }

    private void writeHeaders(DataOutputStream dos,
                              HttpResponse httpResponse) throws IOException {
        for (Map.Entry<String, Object> headerEntry : httpResponse.getResponseHeader().getHeaderEntries()) {
            dos.writeBytes(String.format("%s: %s\r%n", headerEntry.getKey(), headerEntry.getValue()));
        }
    }
}
