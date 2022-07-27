package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.Controller;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final Map<ControllerIdentity, Controller> controllers;

    public RequestHandler(Socket connection, Map<ControllerIdentity, Controller> controllers) {
        this.connection = connection;
        this.controllers = controllers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (
            InputStream inputStream = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {

            var httpRequest = HttpRequest.parse(bufferedReader);

            System.out.println(httpRequest.getUrl());
            var dataOutputStream = new DataOutputStream(out);

            if (httpRequest.isStaticFile()) {
                var httpResponse = HttpResponse.parseStaticFile(httpRequest.getUrl(), httpRequest.getFileExtension());
                httpResponse.write(dataOutputStream);
                return;
            }

            var controller = controllers.get(new ControllerIdentity(httpRequest.getUrl(), httpRequest.getMethod()));
            var response = controller.run(httpRequest);

            response.write(dataOutputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
