package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.Cookie;
import http.request.ContentType;
import http.request.Headers;
import http.request.HttpRequest;
import http.request.RequestLine;
import http.response.HttpResponse;
import utils.FileIoUtils;
import utils.IOUtils;
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
            var httpRequest = parseHttpRequest(bufferedReader);

            if (httpRequest.isStaticFile()) {
                responseStaticFile(new DataOutputStream(out), httpRequest.getUrl(), httpRequest.getFileExtension());
                return;
            }
            var controller = controllers.get(new ControllerIdentity(httpRequest.getUrl(), httpRequest.getMethod()));
            var response = controller.run(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);
            writeHeader(dos, response);
            writeBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest parseHttpRequest(BufferedReader bufferedReader) throws IOException {
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

    private void responseStaticFile(DataOutputStream dos, String path, String fileExtension) {
        try {
            var bytes = FileIoUtils.loadFileFromClasspath(path);
            var contentType = ContentType.of(fileExtension);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType.getMessage() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + bytes.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(bytes);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(DataOutputStream dos, HttpResponse httpResponse) {
        var protocol = httpResponse.getProtocol();
        var httpStatus = httpResponse.getHttpStatus();
        try {
            dos.writeBytes(String.format("%s/%s %d %s \r\n",
                protocol.getProtocolType(),
                protocol.getVersion(),
                httpStatus.getStatusCode(),
                httpStatus.getMessage())
            );
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + httpResponse.getBody().length() + "\r\n");

            for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
                dos.writeBytes(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
            }

            for (Cookie cookie : httpResponse.getCookies()) {
                var prefix = String.format("Set-Cookie: %s=%s", cookie.getKey(), cookie.getValue());

                var cookieResponse = Stream.concat(Stream.of(prefix), cookie.getOptions().stream())
                    .collect(Collectors.joining("; "));

                dos.writeBytes(cookieResponse + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos, HttpResponse response) {
        var body = response.getBody();
        try {
            dos.write(body.getBytes(StandardCharsets.UTF_8), 0, body.length());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
