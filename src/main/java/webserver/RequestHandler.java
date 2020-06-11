package webserver;

import http.handler.Handler;
import http.handler.mapper.HandlerMapper;
import http.request.HttpRequest;
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

            String requestLineStr = br.readLine();
            log.debug("requestLine: {}", requestLineStr);

            if (Objects.isNull(requestLineStr)) {
                return;
            }

            HttpRequest httpRequest = HttpRequest.parse(requestLineStr, br);
            log.debug("httpRequest: {}", StringUtils.toPrettyJson(httpRequest));

            Handler handler = HandlerMapper.getHandler(httpRequest.getPath());
            HttpResponse httpResponse = handler.getHttpResponse(httpRequest);
            ResponseHandler.writeHttpResponse(dos, httpResponse);

            log.debug("httpResponse: {}", StringUtils.toPrettyJson(httpResponse));
        }
        catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }
}
