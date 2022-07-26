package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.request.HttpHeaders;
import webserver.http.request.Path;
import webserver.http.request.Queries;
import webserver.http.request.RequestLine;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            RequestLine requestLine = RequestLine.from(br.readLine());

            List<String> headers = new ArrayList<>();
            String nextLine = br.readLine();
            while (!Objects.isNull(nextLine) && !"".equals(nextLine)) {
                headers.add(nextLine);
                nextLine = br.readLine();
            }
            HttpHeaders httpHeaders = HttpHeaders.from(String.join("\n", headers));

            DataOutputStream dos = new DataOutputStream(out);
            Path path = requestLine.getPath();
            Queries queries = path.getQueries();
            String pathString = path.toString();
            byte[] body;
            switch (pathString) {
                case "user/create":
                    User user = new User(queries.get("userId").get(), queries.get("password").get(), queries.get("name").get(), queries.get("email").get());
                    body = user.toString().getBytes();
                    break;
                default:
                    body = FileIoUtils.loadFileFromClasspath("./templates/" + (!"".equals(pathString) ? pathString : "index.html"));
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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
