package webserver;

import java.io.*;
import java.net.Socket;


import http.RequestMessage;
import http.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.Handler;

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
            RequestMessage requestMessage = RequestMessage.from(new BufferedReader(new InputStreamReader(in)));
            ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(out));
            Handler handler = HandlerMapper.getHandler(requestMessage);
            handler.handle(requestMessage, responseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
