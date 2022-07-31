package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            logger.debug("requst line : {}", line);
            if (line == null) {
                return;
            }
            // Request Line
            RequestLine requestLineParser = new RequestLine(line);
            String url = requestLineParser.getUri().getPath();
            // Request Header
            int contentLength = 0;
            boolean logined = false;
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("header: {}", line);
                HttpHeader httpHeader = new HttpHeader(line);
                if (line.contains("Content-Length")){
                    contentLength = httpHeader.getContentLength();
                }
                if (line.contains("Cookie")) {
                    logined = httpHeader.isLogin();
                }
            }

            if (url.startsWith("/user/create")) {
                new UserCreate(IOUtils.readData(br, contentLength));
                DataOutputStream dos = new DataOutputStream(out);
                HttpResponseWriter.response302Header(dos, "/index.html");
            } else if ("/user/login".equals(url)) {
                String body = IOUtils.readData(br, contentLength);
                QueryStringParser QSParser = new QueryStringParser(body);
                Map<String, String> params = QSParser.getQueryParameters();
                User user = DataBase.findUserById(params.get("userId"));
                DataOutputStream dos = new DataOutputStream(out);
                if (user != null) {
                    if (user.getPassword().equals(params.get("password"))) {
                        HttpResponseWriter.response302CookieHeader(dos, "logined=true", "/index.html");
                    } else {
                        HttpResponseWriter.response302CookieHeader(dos,"logined=false","/user/login_failed.html");
                    }
                } else {
                    HttpResponseWriter.response302CookieHeader(dos,"logined=false","/user/login_failed.html");
                }
            } else if ("/user/list".equals(url)) {
                if (!logined) {
                    HttpResponseWriter.responseResource(out, "/user/login.html");
                }
                Collection<User> users = DataBase.findAll();
                String userList = HandleBarTemplateLoader.load("user/list", users);
                HttpResponseWriter.responseUserListResource(out, userList);
            } else if (url.endsWith(".css")) {
                HttpResponseWriter.responseStaticResource(out, url, "css");
            } else if (url.endsWith(".js")) {
                HttpResponseWriter.responseStaticResource(out, url, "js");
            } else if (url.endsWith(".ttf")) {
                HttpResponseWriter.responseStaticResource(out, url, "fonts");
            } else if (url.endsWith(".woff")) {
                HttpResponseWriter.responseStaticResource(out, url, "fonts");
            } else if (url.endsWith(".svg")) {
                HttpResponseWriter.responseStaticResource(out, url, "fonts");
            }else if (url.endsWith(".png")) {
                HttpResponseWriter.responseStaticResource(out, url, "images");
            } else if (url.endsWith(".ico")) {
                HttpResponseWriter.responseResource(out, url);
            } else {
                HttpResponseWriter.responseResource(out, url);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
