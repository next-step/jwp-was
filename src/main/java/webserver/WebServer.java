package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;


    private final int port;
    private final ExecutorService executorService;
    private final AtomicBoolean running = new AtomicBoolean();

    public WebServer(int port) {
        this(
            port,
            new ThreadPoolExecutor(250, 250, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100))
        );
    }

    public WebServer(int port, ExecutorService executorService) {
        this.port = port;
        this.executorService = executorService;
    }

    public void start() throws Exception {
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(this.port)) {
            logger.info("Web Application Server started {} port.", this.port);
            running.set(true);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while (this.running.get() && (connection = listenSocket.accept()) != null) {
                this.executorService.execute(new RequestHandler(connection));
            }
        }
        this.executorService.shutdown();
    }

    public void stop() {
        this.running.set(false);
    }

    public boolean isRunning() {
        return this.running.get();
    }

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        new WebServer(port).start();
    }

}
