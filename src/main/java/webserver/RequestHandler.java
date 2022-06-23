package webserver;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Controller;
import webserver.handler.ApiHandler;
import webserver.handler.Handler;
import webserver.handler.ResourceHandler;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private static final List<Handler> handlers = Lists.newArrayList(new ApiHandler(), new ResourceHandler());

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpRequest httpRequest = HttpRequest.from(br);


            HttpResponse httpResponse = HttpResponse.response202();
            for (Handler handler : handlers) {
                Controller controller = handler.find(httpRequest);
                if (Objects.nonNull(controller)) {
                    httpResponse = controller.doService(httpRequest);
                    break;
                }
            }

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, httpResponse);
            responseBody(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void response200Header(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %s OK \r\n", httpResponse.getCode()));
            dos.writeBytes(httpResponse.makeResponseHeader());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] body = httpResponse.getBody();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
