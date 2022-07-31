package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.request.domain.model.User;
import webserver.request.domain.request.HttpRequest;
import webserver.request.domain.request.QueryString;
import webserver.request.domain.request.RequestBody;
import webserver.request.domain.request.RequestLine;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private User user;

    private HttpRequest httpRequest;
//    private RequestLine requestLine;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            if(in != null) {
                httpRequest = new HttpRequest(in);

                DataOutputStream dos = new DataOutputStream(out);

                String path = matchResponse(httpRequest);
                byte[] body = path.getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String matchResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String method = httpRequest.getMethod();

        if(method.equals("GET")) {
            String path = httpRequest.getPath();
            if (path.endsWith("html")) {
                return IOUtils.loadFileFromClasspath(path);
            }

            if (httpRequest.getQueryString() != null) {
                return IOUtils.loadFileFromClasspath(parseQueryString(path, httpRequest.getQueryString()));
            }
            else
                return IOUtils.loadFileFromClasspath("/index.html");
        }

        if(method.equals("POST")) {
            String path = httpRequest.getPath();
            return IOUtils.loadFileFromClasspath(parseBody(path, httpRequest.getBody()));
        }

        return "";
    }

    private String parseQueryString(String parsePath, QueryString queryString) {
        String[] str = parsePath.split("/");
        if (str.length > 2) {
            if (str[1].equals("user")) {
                userController(queryString.getDataPairs(), str);
            }
        }

        return "/index.html";
    }

    private String parseBody(String parsePath, RequestBody requestBody) {
        String[] str = parsePath.split("/");
        if(str[1].equals("user")) {
            userController(requestBody.getDataPairs(), str);
        }

        return "/index.html";
    }

    private void userController(Map<String, String> map, String[] str) {
        if(str[2].equals("create")) {
            this.user = User.create(map);
        }
    }

//    private void showRequest(InputStream is) {
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
//            String line = br.readLine();
//            RequestLine requestLine = RequestLine.parse(line);
////            httpRequest.addProperty(line);
//            logger.debug(requestLine.toString());
//
//            StringBuilder stringBuilder = new StringBuilder();
//            while(!line.equals("")) {
//                line = br.readLine();
////                httpRequest.addProperty(line);
//                stringBuilder.append(line + "\n");
//            }
//
//            logger.debug(String.valueOf(stringBuilder));
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }

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
