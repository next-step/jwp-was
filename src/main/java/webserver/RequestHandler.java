package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            logger.debug("requst line : {}", line);
            if (line == null) {
                return;
            }
            // Request Line
            RequestLineParser RLParser = new RequestLineParser(line);
            String path = RLParser.getUri().getPath();
            // Request Header
            int contentLength = 0;
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("header: {}", line);
                if (line.contains("Content-Length")){
                    contentLength = getContentLength(line);
                }
            }

            if (path.startsWith("/user/create")) {
                String body = IOUtils.readData(br, contentLength);
                QueryStringParser QSParser = new QueryStringParser(body);
                Map<String, String> params = QSParser.getQueryParameters();
                User user = new User(params.get("userId"), params.get("password"),params.get("name"),params.get("email"));
                logger.debug("User : {}",user);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private int getContentLength(String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
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

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }
}
