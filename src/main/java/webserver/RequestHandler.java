package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

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
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            RequestLine requestLine = RequestLine.from(br.readLine());

            String line = null;
            List<String> request = new ArrayList<>();

            while (!"".equals(line)) {
                line = br.readLine();
                request.add(line);

                if (Objects.isNull(line)) {
                    break;
                }
            }
            HttpHeader httpHeader = HttpHeader.from(request);

            int contentLength = httpHeader.getContentLength();
            HttpParameter httpBody = HttpParameter.from(IOUtils.readData(br, contentLength));

            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            String path = requestLine.getUri().getPath();

            if ("/user/create".equals(path)) {
                DataBase.addUser(new User(httpBody.get("userId"), httpBody.get("password"), httpBody.get("name"), httpBody.get("email")));
                DataBase.findAll().forEach(user -> logger.debug("{}", user));

                httpResponse.responseRedirect("/index.html");
                return;
            }

            httpResponse.responseOk(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
