package webserver;

import model.HttpMessage;
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

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            List<String> httpMessageData = RequestService.getHttpMessageData(bufferedReader);
            HttpMessage httpMessage = new HttpMessage(httpMessageData, bufferedReader);
            logger.info("Request data >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n" + httpMessage.toStringHttpMessage());

            byte[] body = RequestService.getBody(httpMessage);

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            ResponseService.makeResponseHeader(dataOutputStream, body);
            ResponseService.makeResponseBody(dataOutputStream, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
