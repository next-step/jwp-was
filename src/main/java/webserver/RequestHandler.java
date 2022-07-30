package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.*;

import static java.lang.Integer.parseInt;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String END_OF_LINE = "";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = convertStreamToHttpRequest(in);
            final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();
            final String path = httpRequest.getRequestLine().getUrl().getPath();

            if (httpMethod.equals(HttpMethod.GET) && path.equals("/user/create")) {
                Map<String, String> parameters = httpRequest.getRequestLine().getUrl().getQueryParameter().getParameters();
                doGetSingUp(parameters);
                return;
            }

            if (httpMethod.equals(HttpMethod.POST) && path.equals("/user/create")) {
                doPostSignUp(httpRequest.getRequestBody());
                return;
            }

            final byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getRequestLine().getUrl().getPath());

            final DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void doGetSingUp(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }

    private void doPostSignUp(RequestBody requestBody) {
        Map<String, String> body = requestBody.getContents();
        String userId = body.get("userId");
        String password = body.get("password");
        String name = body.get("name");
        String email = body.get("email");

        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }

    private HttpRequest convertStreamToHttpRequest(InputStream is) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = br.readLine();

        final RequestLine requestLine = RequestLine.parseFrom(line);
        logger.info(requestLine.toString());

        final RequestHeader requestHeader = new RequestHeader();
        while (! line.equals(END_OF_LINE) || line == null) {
            logger.info(line);
            line = br.readLine();
            requestHeader.add(line);
        }

        final RequestBody requestBody = RequestBody.parseFrom(
                IOUtils.readData(br, requestHeader.getContentLength())
        );
        logger.info(requestBody.toString());

        return new HttpRequest(requestLine, requestHeader, requestBody);
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
