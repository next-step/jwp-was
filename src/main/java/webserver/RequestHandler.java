package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = bufferedReader.readLine();
            logger.debug("request line: {}", line);

            Request request = new Request(line);
            Controller controller = match(request.getRequestLine().getPath());

            while (!line.equals("")) {
                line = bufferedReader.readLine();
                logger.debug("header: {}", line);
            }

            Response response;
            try {
                response = controller.serving(request);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = response.getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
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

    public Controller match(String url) {
        if (url.indexOf(".js") != -1 || url.indexOf(".css") != -1 || url.indexOf(".ico") != -1 || url.indexOf(".html") != -1) {
            return new StaticResourceController();
        }

        if (url.indexOf("/user/create") != -1) {
            return new CreatUserController();
        }

        return null;
    }
}
