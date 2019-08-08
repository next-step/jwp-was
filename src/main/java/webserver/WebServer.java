package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final ServletContext SERVLET_CONTEXT = new ServletContext();
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(100);

    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection, SERVLET_CONTEXT);
                THREAD_POOL.execute(requestHandler);
            }
        }
    }
}
