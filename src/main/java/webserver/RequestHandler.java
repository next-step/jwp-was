package webserver;

import http.common.HttpHeaders;
import http.handler.Handler;
import http.handler.mapper.HandlerMapper;
import http.request.HttpRequest;
import http.request.requestline.RequestLine;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class RequestHandler implements Runnable {
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                  connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String request = br.readLine();
            log.debug("request: {}", request);

            if (Objects.isNull(request)) {
                return;
            }

            RequestLine requestLine = RequestLine.of(request);
            log.debug("path: {}", requestLine.getPath());

            HttpRequest httpRequest = HttpRequest.of(br, requestLine, HttpHeaders.of(br));
            log.debug("httpRequest: {}", StringUtils.toPrettyJson(httpRequest));

            Handler handler = HandlerMapper.getHandler(requestLine.getPath());
            HttpResponse httpResponse = handler.getHttpResponse(httpRequest);
            handler.writeHttpResponse(dos, httpResponse);

            log.debug("httpResponse: {}", StringUtils.toPrettyJson(httpResponse));
        }
        catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }
}
