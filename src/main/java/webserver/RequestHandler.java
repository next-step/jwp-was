package webserver;

import controller.Controller;
import controller.UserController;
import exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ResourceViewResolver;
import webserver.http.request.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            HttpRequest request = new HttpRequest(bufferedReader);

            // servlet에서 request, response 파라미터로 받아서 컨트롤러, 뷰에 매핑해주도록 만들자
            Controller userController = UserController.getInstance();
            String path = userController.get(request);
            ResourceViewResolver resourceView = new ResourceViewResolver(path);

            DataOutputStream dos = new DataOutputStream(out);

            if (resourceView.isRedirect()) {
                response302Header(dos, resourceView.getPath());
            } else {
                response200Header(dos, resourceView.getResponseBody().length);
                responseBody(dos, resourceView.getResponseBody());
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found : " + e.getMessage());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, final String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
