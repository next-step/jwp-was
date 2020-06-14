package webserver;

import static webserver.WebServerRunner.DEFAULT_POOL_SIZE;
import static webserver.WebServerRunner.DEFAULT_QUEUE_SIZE;

public class WebServerLauncher {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        }
        else {
            port = Integer.parseInt(args[0]);
        }

        WebServerRunner runner = WebServerRunner.from(DEFAULT_POOL_SIZE, DEFAULT_QUEUE_SIZE);
        runner.execute(port);
    }
}
