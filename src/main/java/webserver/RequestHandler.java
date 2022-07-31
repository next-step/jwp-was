package webserver;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.domain.controller.RequestProcessor;
import webserver.http.domain.exception.NullRequestException;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.view.request.RequestReader;
import webserver.http.view.response.ResponseWriter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestReader requestReader;
    private final ResponseWriter responseWriter;
    private final RequestProcessor requestProcessor;

    public RequestHandler(Socket connectionSocket,
                          RequestReader requestReader,
                          ResponseWriter responseWriter,
                          RequestProcessor requestProcessor) {
        this.connection = connectionSocket;
        this.requestReader = requestReader;
        this.responseWriter = responseWriter;
        this.requestProcessor = requestProcessor;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())
        ) {
            Request request = requestReader.read(bufferedReader);
            logger.info("[request] = {}", request);

            Response response = requestProcessor.process(request);
            responseWriter.write(dos, response);
        } catch (NullRequestException e) {
            logger.warn(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
