package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestHeader;
import response.Response;

import java.io.*;
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

            Controller controller = new Controller();
            RequestMappingHandler requestMappingHandler = new RequestMappingHandler(controller);
            Response response = requestMappingHandler.request(requestHeader);

            DataOutputStream dos = new DataOutputStream(out);
            response.writeWithDos(dos);
            responseBody(dos, response.getBody());
        } catch (Exception e) {
            logger.error("main error", e);
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
