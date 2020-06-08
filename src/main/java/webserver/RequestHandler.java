package webserver;

import java.io.*;
import java.net.Socket;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import http.*;
import http.controller.PathController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            /*PathController controller = ControllerHandler.getPathController(httpRequest);

            //controller 수행
            byte[] body = controller.execute();

            DataOutputStream dos = new DataOutputStream(out);
            responseBody(dos, body);*/
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
