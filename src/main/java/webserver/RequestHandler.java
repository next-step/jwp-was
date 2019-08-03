package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.HttpRequest;
import webserver.http.RequestBody;
import webserver.http.RequestUri;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
            logger.debug("request header : {}", httpRequest);

            RequestUri uri = httpRequest.getRequestLine().getUri();
            DataOutputStream dos = new DataOutputStream(out);

            if ("/user/create".equals(uri.getPath())) {
                String body = IOUtils.readData(bufferedReader, Integer.parseInt(httpRequest.getHeader("Content-Length")));
                RequestBody requestBody = RequestBody.parse(body);

                User user = new User(requestBody.getValue("userId"), requestBody.getValue("password"),
                        requestBody.getValue("name"), requestBody.getValue("email"));
                DataBase.addUser(user);
                logger.debug("User : {}", user);

                response302Header(dos, "/index.html", "logined=false; Path=/");
            } else if("/user/login".equals(uri.getPath())) {
                String body = IOUtils.readData(bufferedReader, Integer.parseInt(httpRequest.getHeader("Content-Length")));
                RequestBody requestBody = RequestBody.parse(body);

                User user = DataBase.findUserById(requestBody.getValue("userId"));

                if(user == null || !user.isPasswordMatch(requestBody.getValue("password"))) {
                    response302Header(dos, "/user/login_failed.html", "logined=false; Path=/");
                } else {
                    response302Header(dos, "/index.html", "logined=true; Path=/");
                }
            } else if("/user/list".equals(uri.getPath())){
                if(httpRequest.getHeader("Cookie").equals("logined=true")) {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    loader.setCharset(Charsets.UTF_8);
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile("user/profile");
                    Collection<User> users = DataBase.findAll();
                    Map<String, Object> data = new HashMap<>();
                    data.put("users", users);

                    String usersPage = template.apply(data);
                    byte[] body = usersPage.getBytes();

                    response200Header(dos, body.length, "text/html;");
                    responseBody(dos, body);

                } else {
                    response302Header(dos, "/user/login.html", "logined=false; Path=/");
                }

            } else {
                String resourcePath = ResourceHandler.getResourcePath(uri);
                byte[] body = ResourceHandler.loadResource(resourcePath);

                response200Header(dos, body.length, ResourceHandler.resourceContentType(uri));
                responseBody(dos, body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200  \r\n");
            dos.writeBytes("Content-Type: " + contentType + "charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
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
