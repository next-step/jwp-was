package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Dispatcher;
import webserver.http.Request;
import webserver.http.Response;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Dispatcher dispatcher;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.dispatcher = new Dispatcher();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            Request request = new Request(br);
            Response response = new Response(dos);

            dispatcher.service(request, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error("", e);
            //404
        }
    }
}
