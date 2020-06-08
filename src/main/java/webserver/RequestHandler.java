package webserver;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URISyntaxException;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import http.*;
import http.controller.PathController;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FieldNameUtils;
import utils.FileIoUtils;
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
            String request = line.trim(); // 첫번째 줄 저장
            String requestBody = StringUtils.EMPTY;

            RequestLine requestLine = RequestLineParser.parse(request);
            HttpHeaderInfo headerInfo = new HttpHeaderInfo();

            logger.debug("request first line : {}", line);

            while (!"".equals(line)) {
                line = br.readLine().trim();
                if(StringUtils.isNotEmpty(line)) {
                    headerInfo.addHeaderValue(line);
                }
                logger.debug("request : {}", line);
            }

            if(StringUtils.isNotEmpty(headerInfo.getValue("Content-Length"))) {
                requestBody = IOUtils.readData(br, Integer.parseInt(headerInfo.getValue("Content-Length")));
            }

            HttpRequest httpRequest = new HttpRequest(requestLine, headerInfo, requestBody);
            PathController controller = ControllerHandler.getPathController(httpRequest);

            //controller 수행
            byte[] body = controller.execute();

            DataOutputStream dos = new DataOutputStream(out);
            responseBody(dos, body);
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
