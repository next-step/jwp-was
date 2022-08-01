package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import handler.PathHandler;
import model.request.HttpRequestHeader;
import model.response.HttpResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.parser.HttpRequestHeaderParser;

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
            HttpRequestHeader httpRequestHeader = HttpRequestHeaderParser.parseHttpRequestHeaderParser(inputStream);

            HandlerSelector handlerSelector = new HandlerSelector();
            PathHandler pathHandler = handlerSelector.selectAvailableHandler(httpRequestHeader);
            HttpResponseHeader httpResponseHeader = pathHandler.Handle(httpRequestHeader);

            responseHeader(dataOutputStream, httpResponseHeader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponseHeader httpResponseHeader) throws IOException {
        dos.writeBytes(httpResponseHeader.getResponseLine() + "\r\n");
        for (String header : httpResponseHeader.getHttpHeaders()) {
            dos.writeBytes(header + "\n");
        }
        dos.writeBytes("\r\n");

        responseBody(dos, httpResponseHeader.getBody());
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
