package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_THREAD_POOL = 10;

    public static void main(String args[]) throws Exception {
        int port = setPort(args);
        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection));
            }
        }
    }

    private static int setPort(String args[]) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        return Integer.parseInt(args[0]);
    }
}
