package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
            HttpHeader httpHeader = parseHeaders(bufferedReader);

            logger.info("IN RequestLine: {}, Headers: {}", requestLine, httpHeader);

            DataOutputStream dos = new DataOutputStream(out);
            if (requestLine.getUrl().getPath().equals("/user/create")) {
                String requestBody = IOUtils.readData(bufferedReader, Integer.parseInt(httpHeader.get("Content-Length")));
                logger.info("RequestBody {}", requestBody);

                String[] splitRequestBody = requestBody.split("&");
                Map<String, String> parameters = Arrays.stream(splitRequestBody)
                        .collect(Collectors.toMap((data) -> data.split("=")[0], (data) -> data.split("=")[1]));

                String userId = parameters.get("userId");
                String password = parameters.get("password");
                String name = parameters.get("name");
                String email = parameters.get("email");
                DataBase.addUser(new User(userId, password, name, email));
                logger.info("allUsers {}", DataBase.findAll());

                byte[] body = FileIoUtils.loadFileFromClasspath("templates/index.html");
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else {
                byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestLine.getUrl().getPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpHeader parseHeaders(BufferedReader bufferedReader) throws IOException {
        HttpHeader httpHeader = new HttpHeader();
        String headerInfo;
        while ((headerInfo = bufferedReader.readLine()) != null) {
            if(headerInfo.isEmpty()){
                break;
            }
            httpHeader.add(headerInfo);
        }
        return httpHeader;
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
