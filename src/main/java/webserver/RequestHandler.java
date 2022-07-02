package webserver;

import webserver.http.RequestController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = RequestFactory.create(new BufferedReader(new InputStreamReader(in, "UTF-8")));
            RequestController controller = RequestControllerContainer.match(httpRequest);
            HttpResponse httpResponse = controller.service(httpRequest);
            DataOutputStream dos = new DataOutputStream(out);
            write(dos, httpResponse.getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private void write(DataOutputStream dos, byte[] response) {
        try {
            dos.write(response, 0, response.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
