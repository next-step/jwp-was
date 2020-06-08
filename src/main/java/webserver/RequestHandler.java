package webserver;

import com.google.common.collect.Maps;
import http.HttpHeader;
import http.HttpMethod;
import http.exception.MethodNotAllowedException;
import http.handler.Handler;
import http.request.HttpRequest;
import http.handler.mapper.HandlerMapper;
import http.request.requestline.RequestLine;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import static http.HttpHeader.CONTENT_LENGTH_NAME;

@Slf4j
public class RequestHandler implements Runnable {
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                  connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String request = br.readLine();
            log.debug("request: {}", request);

            if (Objects.isNull(request)) {
                return;
            }

            RequestLine requestLine = RequestLine.of(request);
            log.debug("path: {}", requestLine.getPath());

            HttpRequest httpRequest = getHttpRequest(br, requestLine, getHttpHeaders(br));

            Handler handler = HandlerMapper.getHandler(requestLine.getPath());
            HttpResponse httpResponse = handler.getResponse(httpRequest);
            handler.writeResponse(dos, httpResponse);
        }
        catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private Map<String, String> getHttpHeaders(BufferedReader br) throws IOException {
        Map<String, String> httpHeaders = Maps.newHashMap();
        String httpHeaderLine = br.readLine();

        while (!"".equals(httpHeaderLine)) {
            log.debug("httpHeader: {}", httpHeaderLine);

            HttpHeader header = HttpHeader.of(httpHeaderLine);
            httpHeaders.put(header.getHeaderName(), header.getHeaderValue());
            httpHeaderLine = br.readLine();
        }

        return httpHeaders;
    }

    private HttpRequest getHttpRequest(BufferedReader br, RequestLine requestLine, Map<String, String> httpHeaders) throws IOException {
        if (HttpMethod.POST.equals(requestLine.getMethod())) {
            String httpBody = getHttpBody(br, StringUtils.toInt(httpHeaders.get(CONTENT_LENGTH_NAME)));
            return HttpRequest.ofPost(requestLine, httpHeaders, httpBody);
        }

        if (HttpMethod.GET.equals(requestLine.getMethod())) {
            return HttpRequest.ofGet(requestLine, httpHeaders);
        }

        throw new MethodNotAllowedException();
    }

    private String getHttpBody(BufferedReader br, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        br.read(buffer, 0, contentLength);

        String httpBody = new String(buffer, 0, buffer.length);
        log.debug("httpBody: {}", httpBody);
        return httpBody;
    }
}
