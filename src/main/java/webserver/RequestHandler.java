package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.Headers;
import http.request.HttpRequest;
import http.request.RequestLine;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.controller.Controller;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final Map<Resource, Controller> controllers;

    public RequestHandler(Socket connection, Map<Resource, Controller> controllers) {
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
            var httpRequest = getRequestLine(bufferedReader);

            if (httpRequest.isStaticFile()) {
                responseStaticFile(new DataOutputStream(out), httpRequest.getUrl());
                return;
            }
            var controller = controllers.get(new Resource(httpRequest.getUrl(), httpRequest.getMethod()));
            controller.run(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest getRequestLine(BufferedReader bufferedReader) throws IOException {
        String line = IOUtils.readSingleLine(bufferedReader);
        var requestLine = new RequestLine(line);
        var headers = new Headers(IOUtils.readLines(bufferedReader));

        String body = readBody(headers, bufferedReader);

        return new HttpRequest(requestLine, headers, body);
    }

    private String readBody(Headers headers, BufferedReader bufferedReader) {
        if (headers.hasBody()) {
            return IOUtils.readData(bufferedReader, headers.contentLength());
        }
        return "";
    }

    private void responseStaticFile(DataOutputStream dos, String path) {
        try {
            var bytes = FileIoUtils.loadFileFromClasspath(path);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + bytes.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(bytes);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
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
