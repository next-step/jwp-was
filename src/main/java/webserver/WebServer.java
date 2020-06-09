package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.InMemorySessionHolder;
import session.SessionManager;
import webserver.processor.Processors;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final Processors PROCESSORS = new Processors();
    private static final SessionManager SESSION_MANAGER = new SessionManager(new InMemorySessionHolder());
    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            50, 250, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100)
    );


    public static void main(String[] args) throws Exception {
        int port = initPort(args);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                execute(connection);
            }
        }
    }

    private static void execute(final Socket connection) {
        try {
            THREAD_POOL.execute(new RequestHandler(connection, SESSION_MANAGER, PROCESSORS));
        } catch (RejectedExecutionException e) {
            logger.error("Request rejected cuz worker queue is full");
        }
    }

    private static int initPort(String[] args) {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }
}
