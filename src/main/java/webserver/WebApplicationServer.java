package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.config.WebServerConfig;
import webserver.http.domain.controller.Controller;
import webserver.http.domain.controller.HttpRequestProcessor;
import webserver.http.view.request.RequestReader;
import webserver.http.view.response.ResponseWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;

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
            HttpRequestProcessor requestProcessor = WebServerConfig.requestProcessor(controllers);

            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(
                        new RequestHandler(
                                connection,
                                requestReader,
                                responseWriter,
                                requestProcessor
                        )
                );

                thread.start();
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
