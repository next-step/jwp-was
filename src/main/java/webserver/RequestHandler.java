package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.HttpController;
import webserver.http.HttpResponse;
import webserver.http.RequestLine;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        HttpController httpController = new HttpController();
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String content = IOUtils.readData(new BufferedReader(new InputStreamReader(in, "UTF-8")), 1024);

            logger.debug(content);
            HttpResponse response = RequestLine.parse(httpController, content);
            DataOutputStream dos = new DataOutputStream(out);
            response.sendResponse(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
