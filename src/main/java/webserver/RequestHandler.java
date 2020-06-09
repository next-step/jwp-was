package webserver;

import db.DataBase;
import http.QueryString;
import http.RequestLine;
import http.response.HttpResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpResponse response = new HttpResponse(out);

            String line = br.readLine();
            logger.debug("request line : {}", line);
            RequestLine requestLine = RequestLine.of(line);
            String path = requestLine.getPath();
            Map<String, String> headers = new HashMap<>();
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("header : {}", line);
                String[] headerValues = line.split(": ");
                if (headerValues.length == 2) {
                    headers.put(headerValues[0], headerValues[1]);
                }
            }

            logger.debug("Content-Length: {}", headers.get("Content-Length"));

            if ("/user/create".equals(path)) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                QueryString queryString = QueryString.of(requestBody);
                User user = new User(queryString.getPrameter("userId"), queryString.getPrameter("password"),
                    queryString.getPrameter("name"), queryString.getPrameter("email"));
                logger.debug("User : {}", user);
                DataBase.addUser(user);

                response.redirect("/index.html");
            } else {
                response.response(path);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
