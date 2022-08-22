package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Router router;

    public RequestHandler(Socket connectionSocket, Router router) {
        this.connection = connectionSocket;
        this.router = router;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream is = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            final HttpRequest httpRequest = HttpRequest.parseFrom(br);
            logger.debug(httpRequest.toString());

            HttpResponse httpResponse = router.execute(httpRequest);

            final DataOutputStream dos = new DataOutputStream(out);
            httpResponse.write(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse route(final HttpRequest httpRequest){
        return router.execute(httpRequest);
    }
}
