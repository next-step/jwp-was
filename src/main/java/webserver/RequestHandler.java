package webserver;

import model.HttpRequestMessage;
import model.HttpResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.RequestService;
import service.ResponseService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {

            HttpRequestMessage httpRequestMessage = this.getHttpRequestMessage(inputStream, outputStream);
            HttpResponseMessage httpResponseMessage = RequestService.getClientResponse(httpRequestMessage);

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            this.sendResponse(dataOutputStream, httpResponseMessage);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private HttpRequestMessage getHttpRequestMessage(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        List<String> httpMessageData = RequestService.getHttpMessageData(bufferedReader);
        return new HttpRequestMessage(httpMessageData, bufferedReader);
    }

    private void sendResponse(OutputStream outputStream, HttpResponseMessage httpResponseMessage) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        ResponseService.makeResponseHeader(dataOutputStream, httpResponseMessage);
        ResponseService.makeResponseBody(dataOutputStream, httpResponseMessage);
    }

}
