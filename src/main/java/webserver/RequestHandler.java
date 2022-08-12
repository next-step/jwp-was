package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = new HttpRequest(br);

            HttpResponse httpResponse = RequestMapping.process(httpRequest);
            response(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.response());
            dos.write(httpResponse.getBody(), 0, httpResponse.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
