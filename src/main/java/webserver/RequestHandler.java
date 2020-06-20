package webserver;

import java.io.*;
import java.net.Socket;


import handler.Handler;
import http.request.RequestMessage;
import http.response.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Handler dispatcherHandler;

    public RequestHandler(Socket connectionSocket, Handler dispatcherHandler) {
        this.connection = connectionSocket;
        this.dispatcherHandler = dispatcherHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestMessage requestMessage = RequestMessage.from(new BufferedReader(new InputStreamReader(in)));
            ResponseMessage responseMessage = new ResponseMessage(new DataOutputStream(out));

            dispatcherHandler.service(requestMessage, responseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
