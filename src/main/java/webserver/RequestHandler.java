package webserver;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URISyntaxException;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import http.QueryString;
import http.RequestLine;
import http.RequestLineParser;
import http.ResourcePathMaker;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FieldNameUtils;
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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            String request = line.trim(); // 첫번째 줄 저장
            String header = StringUtils.EMPTY; // 첫번째 줄을 제외한 나머지 정보 저장

            logger.debug("request first line : {}", line);

            while (!"".equals(line)) {
                if(StringUtils.isNotEmpty(header)) {
                    header.concat("\n");
                }
                line = br.readLine().trim();
                header.concat(line);
                logger.debug("request : {}", line);
            }

            RequestLine requestLine = RequestLineParser.parse(request);

            if(requestLine.isSignRequest()) {
                QueryString queryString = requestLine.getQueryString();
                User user = new User(queryString.getParameter("userId"), queryString.getParameter("password"), queryString.getParameter("name"), queryString.getParameter("email"));
                DataBase.addUser(user);
                return;
            }

            String resourcePath = ResourcePathMaker.makeTemplatePath(requestLine.getPath());

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body =  FileIoUtils.loadFileFromClasspath(resourcePath);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
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
