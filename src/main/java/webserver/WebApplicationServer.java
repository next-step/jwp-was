package webserver;

import configuration.ThreadConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {

        try (ServerSocket listenSocket = new ServerSocket(getPort(args))) {
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Socket acceptedConnection = connection;
                ThreadConfiguration.serviceThreadPool.execute(() -> {
                    RequestHandler requestHandler = new RequestHandler(acceptedConnection);
                    requestHandler.run();
                });
            }
        }
    }

    private static Integer getPort(String[] args) {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        return port;
    }

}
