package webserver;

import db.DataBase;
import http.QueryStrings;
import http.RequestLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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
            RequestLine requestLine = new RequestLine(line);
            String url = requestLine.getStringPath();
            Map<String, String> headers = new HashMap<>();
            while (!"".equals(line)) {
                if (line == null) {
                    return;
                }
                line = br.readLine();
                String[] split = line.split(": ");
                if (split.length == 2) {
                    headers.put(split[0], split[1]);
                }
            }

            if (url.startsWith("/users")) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                Map<String, String> map = QueryStrings.parseQueryStrings(requestBody);
                User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
                DataBase.addUser(user);
                logger.debug("user: {}", user);
                url = "/index.html";
                DataOutputStream dos = new DataOutputStream(out);
                logger.debug("url: {}", url);
                byte[] body = FileIoUtils.loadFileFromClasspath(url);
                response302Header(dos, url);
                responseBody(dos, body);
            }else {
                DataOutputStream dos = new DataOutputStream(out);
                logger.debug("url: {}", url);
                byte[] body = FileIoUtils.loadFileFromClasspath(url);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url){
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + url + "\r\n");
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
