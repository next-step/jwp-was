package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.RequestPathNotFoundException;
import webserver.http.controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.service.Service;
import webserver.http.service.get.GetService;
import webserver.http.service.post.PostService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final HandlerMapping handlerMapping = new HandlerMapping();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = HttpRequest.of(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(httpRequest);

            doService(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = handlerMapping.getHandler(httpRequest.getPath());
        controller.service(httpRequest, httpResponse);
    }

    private void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            httpResponse.toResponseHeader().forEach(header -> {
                try {
                    dos.writeBytes(header + "\r\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.write(httpResponse.getBody(), 0, httpResponse.getLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
