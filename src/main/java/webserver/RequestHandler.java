package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import http.HttpRequest;
import http.HttpResponse;
import http.QueryStrings;
import http.RequestLineParser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();
            logger.debug("requestline : {}",line);

            HttpRequest httpRequest = new HttpRequest(RequestLineParser.parse(line));

            String path = httpRequest.getPath();
            int contentLength = 0;
            while(!line.equals("")) {
                line = br.readLine();
                logger.debug("header : {}", line);
                if(line.contains("Content-Length")) {
                    String value = line.split(" ")[1];
                    contentLength = Integer.parseInt(value);
                }
            }

            if(path.equals("/user/create")) {
                String body = IOUtils.readData(br, contentLength);

                QueryStrings queryStrings = new QueryStrings(body);

                User user = new User(queryStrings.getParameter("userId"),
                        queryStrings.getParameter("password"), queryStrings.getParameter("name"),
                        queryStrings.getParameter("email"));
                /*User user = new User(httpRequest.getParameter("userId"),
                        httpRequest.getParameter("password"), httpRequest.getParameter("name"),
                        httpRequest.getParameter("email"));*/
                logger.debug("User : {}", user);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html" );
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = HttpResponse.getBody(path);

                //byte[] body = "Hello World".getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
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

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
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
