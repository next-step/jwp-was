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
import webserver.request.RequestLine;

public class Controller implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private Socket connection;

    public Controller(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            handleRequest(
                    RequestLine.from(br.readLine()),
                    new Service(new DataOutputStream(out))
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(RequestLine requestLine, Service service) {
        service.helloWorld();
    }
}
