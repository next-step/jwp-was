package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static webserver.WebServerThreadPoolExecutor.DEFAULT_POOL_SIZE;
import static webserver.WebServerThreadPoolExecutor.DEFAULT_QUEUE_SIZE;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        }
        else {
            port = Integer.parseInt(args[0]);
        }

        WebServerThreadPoolExecutor executor = WebServerThreadPoolExecutor.from(DEFAULT_POOL_SIZE, DEFAULT_QUEUE_SIZE);
        executor.runServer(port);
    }
}
