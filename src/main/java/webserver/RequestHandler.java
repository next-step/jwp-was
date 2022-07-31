package webserver;

import java.io.*;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.HandlerMapper;
import webserver.request.HttpHeader;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

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

            HttpRequest httpRequest = getRequest(in);

            HandlerMapper handlerMapper = new HandlerMapper();

            HttpResponse httpResponse = handlerMapper.handle(httpRequest);

            response(out, httpResponse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private HttpRequest getRequest(InputStream in) throws IOException {
        List<String> request = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line = br.readLine();
        request.add(line);

        while (!line.equals("")) {
            line = br.readLine();
            request.add(line);
        }

        HttpRequest httpRequest = new HttpRequest(request);
        // refactoring 필요
        String contentLength = httpRequest.getHeaders().get("Content-Length");

        if (contentLength != null) {
            String body = IOUtils.readData(br, Integer.parseInt(contentLength));
            httpRequest.setBody(new RequestBody(body));
        }

        return httpRequest;
    }

    private void response(OutputStream out, HttpResponse response) {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = response.getBody();

        try {
            dos.writeBytes(String.format("HTTP/1.1 %s \r\n", response.getStatusCode()));
            responseHeader(dos, response.getHeaders());
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes(String.format("Content-Length: %s \r\n", body.length));
            dos.writeBytes("\r\n");
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpHeader headers) throws IOException {
        for (Map.Entry<String, String> header : headers.getHeaders().entrySet()) {
            System.out.printf("%s: %s \r\n%n", header.getKey(), header.getValue());
            dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
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
