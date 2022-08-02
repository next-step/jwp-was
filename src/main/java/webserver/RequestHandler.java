package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

//            String url = httpRequest.getPath();
//            if (url.startsWith("/user/create")) {
//                new UserCreate(IOUtils.readData(br, contentLength));
//                DataOutputStream dos = new DataOutputStream(out);
//                HttpResponseWriter.response302Header(dos, "/index.html");
//            } else if ("/user/login".equals(url)) {
//                String body = IOUtils.readData(br, contentLength);
//                QueryStringParser queryStringParser = new QueryStringParser(body);
//                Map<String, String> params = queryStringParser.getQueryParameters();
//                User user = DataBase.findUserById(params.get("userId"));
//                DataOutputStream dos = new DataOutputStream(out);
//                if (user != null) {
//                    if (user.getPassword().equals(params.get("password"))) {
//                        HttpResponseWriter.response302CookieHeader(dos, "logined=true", "/index.html");
//                    } else {
//                        HttpResponseWriter.response302CookieHeader(dos,"logined=false","/user/login_failed.html");
//                    }
//                } else {
//                    HttpResponseWriter.response302CookieHeader(dos,"logined=false","/user/login_failed.html");
//                }
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
