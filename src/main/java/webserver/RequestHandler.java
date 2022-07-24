package webserver;

import static webserver.response.HttpStatusResponse.responseBodRequest400;
import static webserver.response.HttpStatusResponse.responseNotFound404;
import static webserver.response.HttpStatusResponse.responseOk200;

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
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.supporter.SupportApis;
import webserver.supporter.SupportTemplates;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String PATH_TEMPLATES = "./templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = parseRequest(inputStream);

            if (SupportApis.isSupported(httpRequest.getPath())) {
                if (httpRequest.getPath().equals("/user/create")) {
                    Map<String, String> queryStrings = httpRequest.getQueryStringsMap();
                    User user = new User(queryStrings.get("userId"), queryStrings.get("password"), queryStrings.get("name"), queryStrings.get("email"));
                    DataBase.addUser(user);

                    responseOk200(dos, user.toString().getBytes(StandardCharsets.UTF_8));
                }
                return;
            }

            byte[] body;
            if (SupportTemplates.isSupported(httpRequest.getPath())) {
                body = FileIoUtils.loadFileFromClasspath(PATH_TEMPLATES + httpRequest.getPath());
                responseOk200(dos, body);
                return;
            }

            responseNotFound404(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (RuntimeException e) {  // 예상되는 예외의 최상위 클래스로 할 것 정의해야함
            try (OutputStream out = connection.getOutputStream()) {
                responseBodRequest400(new DataOutputStream(out));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    private HttpRequest parseRequest(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            line = br.readLine();
            HttpRequest httpRequest = new HttpRequest(line);
            logger.debug("RequestLine: " + line);
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("Headers: " + line);
            }

            return httpRequest;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        throw new IllegalStateException();
    }


}
