package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.request.header.HttpHeader;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;

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
            RequestLine requestLine = RequestLine.parse(line);
            byte[] body = {};

            HttpHeader header = new HttpHeader();
            while (line != null && !line.equals("")) {
                line = br.readLine();
                if (line.equals("")) {
                    break;
                }
                header.setField(line);
            }

            int contentLength = header.getContentLength();

            if (requestLine.isMethodEqual(Method.POST) && requestLine.getPath().equals("/user/create")) {
                String bodyString = IOUtils.readData(br, contentLength);
                QueryString queryString = QueryString.parse(bodyString);
                User user = new User(
                        queryString.getValue("userId"),
                        queryString.getValue("password"),
                        queryString.getValue("name"),
                        queryString.getValue("email")
                );
                DataBase.addUser(user);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos);
                responseBody(dos, body);
                return;
            }

            if (requestLine.isFilePath()) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + requestLine.getPath());
            }
            DataOutputStream dos = new DataOutputStream(out);
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

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html \r\n");
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
