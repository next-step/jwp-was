package webserver;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.domain.controller.RequestProcessor;
import webserver.http.domain.exception.BadRequestException;
import webserver.http.domain.exception.NullRequestException;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.StatusCode;
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
            Response response = processRequest(bufferedReader);
            responseWriter.write(dos, response);
        } catch (NullRequestException e) {
            logger.warn(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Response processRequest(BufferedReader bufferedReader) throws IOException {
        try {
            Request request = requestReader.read(bufferedReader);
            logger.info("[request] = {}", request);
            return requestProcessor.process(request);
        } catch (BadRequestException e) {
            logger.warn("[bad request] = {}", e.getMessage(), e);
            return getResponse(StatusCode.BAD_REQUEST, "잘못된 요청입니다. ;(");
        } catch (RuntimeException e) {
            logger.error("[internal error] - 요청값 처리중 에러 발생 = {}", e.getMessage(), e);
            return getResponse(StatusCode.INTERNAL_ERROR, "서버 내부에 오류가 발생했습니다. ;(");
        }
    }

    private Response getResponse(StatusCode statusCode, String text) {
        Response resourceNotFound = Response.from(statusCode);
        resourceNotFound.addHeader("Content-Type", "text/html");
        resourceNotFound.addBody("<h1><meta charset=\"UTF-8\">" + text + "</h1>");
        return resourceNotFound;
    }
}
