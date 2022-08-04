package webserver;

import model.HttpRequestMessage;
import model.HttpResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.RequestService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {

            HttpRequestMessage httpRequestMessage = new HttpRequestMessage(new BufferedReader(new InputStreamReader(inputStream)));
            HttpResponseMessage httpResponseMessage = RequestService.getClientResponse(httpRequestMessage);

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            httpResponseMessage.sendResponse(dataOutputStream);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
