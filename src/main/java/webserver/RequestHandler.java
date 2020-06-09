package webserver;

import java.io.*;
import java.net.Socket;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.HttpRequestHandler;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpRequestHandler httpRequestHandler;
    private HttpSessionManager sessionManager;

    public RequestHandler(Socket connectionSocket, HttpRequestHandler httpRequestHandler, HttpSessionManager sessionManager) {
        this.connection = connectionSocket;
        this.httpRequestHandler = httpRequestHandler;
        this.sessionManager = sessionManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            HttpRequest httpRequest = HttpRequest.from(bufferedReader)
                    .setSessionManager(sessionManager);
            HttpResponse httpResponse = HttpResponse.from(dos);

            httpRequest.linkHttpResponse(httpResponse);

            httpRequestHandler.handleRequest(httpRequest, httpResponse);

            if(!httpResponse.isResponseDone()) {
                response404(dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
