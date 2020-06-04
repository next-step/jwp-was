package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import db.DataBase;
import http.QueryString;
import http.RequestLine;
import http.RequestLineParser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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

            String line = br.readLine();
            logger.debug("Request Line :: {}", line);
            RequestLine requestLine = RequestLineParser.parse(line);

            while(!line.equals("")) {
                line = br.readLine();
                logger.debug("Header :: {}", line);
            }

            String path = requestLine.getPath();
            if ("/user/create".equals(path)) {
                QueryString queryString = requestLine.getQueryString();

                String userId = queryString.getParameterValue("userId");
                String password = queryString.getParameterValue("password");
                String name = queryString.getParameterValue("name");
                String email = queryString.getParameterValue("email");

                DataBase.addUser(new User(userId, password, name, email));
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + path);
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
