package webserver;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.QueryString;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpResponse response = new HttpResponse(out);
            HttpRequest request = new HttpRequest(br);

            String path = request.getPath();

            logger.debug("Content-Length: {}", request.getHeader("Content-Length"));

            if ("/user/create".equals(path)) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(request.getHeader("Content-Length")));
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
