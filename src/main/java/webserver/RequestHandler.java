package webserver;

import db.DataBase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.HttpHeaders;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HttpMethod;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String PATH_DELIMITER = "/";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            logger.debug("request line : {}", line);
            RequestLine requestLine = new RequestLine(line);
            requestLine = requestLine.parse();
            List<String> headerLine = new ArrayList<>();
            line = br.readLine();
            while (!line.equals("")) {
                headerLine.add(line);
                line = br.readLine();
                logger.debug("header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);

            String path = requestLine.getHttpPath().getPath();
            logger.debug("http path : {}", path);

            HttpHeaders httpHeaders = new HttpHeaders(String.join("\n", headerLine));

            RequestParameters requestParameters = new RequestParameters();
            if (HttpMethod.POST.equals(requestLine.getMethod())) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(httpHeaders.get("Content-Length")));
                requestParameters = new RequestParameters(requestBody);
            } else if (HttpMethod.GET.equals(requestLine.getMethod())) {
                requestParameters = requestLine.getRequestParameters();
            }

            Map<String, String> parameters = requestParameters.getRequestParameters();

            if (path.endsWith(".html")) {
                path = "./templates" + path;
                byte[] body = FileIoUtils.loadFileFromClasspath(path);
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else if ("/user/create".equals(path)) {
                User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
                DataBase.addUser(user);

                response302Header(dos, "index.html");
            }

            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            responseHeader(dos, "Location", PATH_DELIMITER + location);
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

    private void responseHeader(DataOutputStream dos, String key, String value) {
        try {
            dos.writeBytes(key + ": " + value + "\r\n");
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
