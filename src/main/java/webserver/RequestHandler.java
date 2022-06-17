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

            while (!line.equals("")) {
                line = bufferedReader.readLine();
                logger.debug("header: {}", line);
            }

            Response response;
            try {
                response = DispatcherServlet.match(request);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            DataOutputStream dos = new DataOutputStream(out);
            write(dos, response.getBytes());
        } catch (IOException e) {
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
