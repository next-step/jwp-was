package webserver;

import controller.FrontController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.BufferedReaderToHttpRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);

			HttpRequest request = bufferedReaderToHttpRequest.parsed();
			HttpResponse response = new HttpResponse(out);
			new FrontController().service(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
