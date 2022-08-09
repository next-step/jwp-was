package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import handler.PathHandler;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpRequestMessage httpRequestMessage = HttpRequestMessage.from(inputStream);

            HandlerSelector handlerSelector = new HandlerSelector();
            PathHandler pathHandler = handlerSelector.selectAvailableHandler(httpRequestMessage);
            HttpResponseMessage httpResponseMessage = pathHandler.handle(httpRequestMessage);

            responseHeader(dataOutputStream, httpResponseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponseMessage httpResponseMessage) throws IOException {
        dos.writeBytes(httpResponseMessage.getResponseLine() + "\r\n");
        for (String header : httpResponseMessage.getHttpHeaders()) {
            dos.writeBytes(header + "\n");
        }
        dos.writeBytes("\r\n");

        responseBody(dos, httpResponseMessage.getBody());
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
