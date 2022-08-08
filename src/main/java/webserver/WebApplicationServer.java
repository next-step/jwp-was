package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        WebApplicationConfig webApplicationConfig = new WebApplicationConfig(250);
        var executorService = new ThreadPoolConfig(webApplicationConfig)
            .create();

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection));
            }
        } catch (Exception e) {
            logger.info("{}", e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }
}
