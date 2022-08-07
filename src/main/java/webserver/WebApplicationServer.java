package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.config.WebServerConfig;
import webserver.http.controller.Controller;
import webserver.http.controller.RequestProcessor;
import webserver.http.view.request.RequestReader;
import webserver.http.view.response.ResponseWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int CORE_POOL_SIZE = 250;
    private static final int KEEP_ALIVE_TIME = 0;
    private static final int CAPACITY = 100;


    private final List<Controller> controllers;

    public WebApplicationServer(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public void run(String args[]) {
        int port = bindPort(args);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            RequestReader requestReader = WebServerConfig.requestReader();
            ResponseWriter responseWriter = WebServerConfig.responseWriter();
            RequestProcessor requestProcessor = WebServerConfig.sessionCompositeRequestProcessor(controllers);

            ExecutorService executorService = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    CORE_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(CAPACITY)
            );

            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(
                        new RequestHandler(
                                connection,
                                requestReader,
                                responseWriter,
                                requestProcessor
                        )
                );
            }
        } catch (IOException e) {
            logger.error("[Server Socket Error]: 소켓 입출력 에러 발생", e);
        }
    }

    private int bindPort(String[] args) {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }
}
