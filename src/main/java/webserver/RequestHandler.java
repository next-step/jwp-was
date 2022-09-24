package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseBody;
import webserver.http.response.ResponseLine;
import webserver.http.response.ResponseViewFactory;
import webserver.servlet.RequestMappingHandler;
import webserver.servlet.ServletConfig;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.parse(in);

            DataOutputStream dos = new DataOutputStream(out);

            if (httpRequest.isStaticResource()) {
                ResponseLine responseLine = ResponseLine.ok();

                String filePath = httpRequest.getRequestLine().getPathValue();
                ResponseBody responseBody = ResponseBody.from(filePath);

                HttpHeaders httpHeaders = HttpHeaders.init();
                httpHeaders.addResponseHeader(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8");
                httpHeaders.addResponseHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(responseBody.getFileLength()));

                HttpResponse httpResponse = new HttpResponse(responseLine, httpHeaders, responseBody);

                ResponseViewFactory.write(dos, httpResponse);
                return;
            }

            RequestMappingHandler requestMappingHandler = new RequestMappingHandler(ServletConfig.servlets());
            HttpResponse httpResponse = requestMappingHandler.doService(httpRequest);

            ResponseViewFactory.write(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
