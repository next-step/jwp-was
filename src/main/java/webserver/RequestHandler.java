package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestHeader;
import response.Response;
import webserver.handler.RequestEngine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            RequestHeader requestHeader = new RequestHeader(bufferedReader);
            logger.debug(requestHeader.toString());

            RequestEngine requestEngine = new RequestEngine();
            Response response = requestEngine.run(requestHeader);
            response.write(out);
        } catch (Exception e) {
            logger.error("main error", e);
        }
    }

}
