package webserver;

import http.controller.AmazingController;
import http.parsers.RequestContextParser;
import http.requests.HttpRequest;
import http.responses.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * TODO: 이 친구는 다음과 같이 리팩토링이 필요함 (나중)
 * 이름은 RequestHandler지만 요청과 응답을 다 받고 있음.
 * 1. RequestHandler -> Dispatcher [rename]
 * 2. request, response 핸들러는 따로 만드는 방향이 맞을 듯
 * 3. request, response는 context로 추상화 하는 방향으로 (scope에 따라 유연하게 다룰 수 있게)
 */
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
            final HttpRequest httpRequest = RequestContextParser.parse(in);
            logger.debug("request context: {}", httpRequest);

            final DataOutputStream dos = new DataOutputStream(out);

            final HttpResponse httpResponse = AmazingController.dispatch(httpRequest);
            logger.debug("response: {}", httpResponse);

            writeResponseContext(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseContext(DataOutputStream dos, HttpResponse context) {
        try {
            dos.writeBytes(context.getStatusLine());
            for (String header : context.getResponseHeaderList()) {
                dos.writeBytes(header);
            }
            dos.writeBytes("\r\n");
            final byte[] responseBody = context.getResponseBody();
            if (responseBody != null) {
                dos.write(responseBody, 0, responseBody.length);
                dos.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
