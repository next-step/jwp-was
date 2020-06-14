package webserver;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_CORE_POOL_SIZE = 10;
    private static final int DEFAULT_MAX_POOL_SIZE = 250;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 60;
    private static final int DEFAULT_QUEUE_CAPACITY = 100;

    public static void main(String[] args) throws Exception {
        final int port = ArrayUtils.isEmpty(args) ? DEFAULT_PORT : Integer.parseInt(args[0]);
        final int corePoolSize = args.length != 5 ? DEFAULT_CORE_POOL_SIZE : Integer.parseInt(args[1]);
        final int maxPoolSize = args.length != 5 ? DEFAULT_MAX_POOL_SIZE : Integer.parseInt(args[2]);
        final int keepAliveTime = args.length != 5 ? DEFAULT_KEEP_ALIVE_TIME : Integer.parseInt(args[3]);
        final int blockQueueCapacity = args.length != 5 ? DEFAULT_QUEUE_CAPACITY : Integer.parseInt(args[4]);

        ThreadPoolConfiguration config = new ThreadPoolConfiguration(corePoolSize, maxPoolSize, keepAliveTime, blockQueueCapacity);
        Executor executor = ConfiguredThreadPoolExecutorWrapper.newConfiguredThreadPool(config);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executor.execute(new RequestHandler(connection));
            }
        }
    }
}
