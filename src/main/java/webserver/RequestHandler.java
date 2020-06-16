package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.*;
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
            BufferedReader br = new BufferedReader((new InputStreamReader(in, "UTF-8")));
            String line = br.readLine();
            String url = null;
            RequestHeaderLine requestHeaderLine = null;
            int contentLength = 0;
            boolean isLoginSuccess = false;
            boolean isCreateSuccess = false;
            Cookie cookie = null;

            while(line != null) {
                logger.debug("line : {}", line);
                RequestHeaderLineType requestHeaderLineType = RequestHeaderLineType.parse(line);
                if (requestHeaderLineType == RequestHeaderLineType.FIRST_LINE) {
                    requestHeaderLine = RequestHeaderLine.of(line);
                    url = requestHeaderLine.getPath().getUrl();
                }
                if (requestHeaderLine == null || requestHeaderLine.getMethod() == null)
                    break;
                if (requestHeaderLineType == RequestHeaderLineType.DO_NOT_PARSE_LINE)
                    break;
                if ((requestHeaderLine.getMethod() == HttpMethod.GET) &&
                        (requestHeaderLineType == RequestHeaderLineType.EMPTY_LINE))
                    break;
                if ((requestHeaderLine.getMethod() == HttpMethod.POST) &&
                        (requestHeaderLineType == RequestHeaderLineType.CONTENT_LENGTH_LINE))
                    contentLength = Integer.parseInt(line.split(" ")[1]);
                if ((requestHeaderLine.getMethod() == HttpMethod.POST) &&
                        (requestHeaderLineType == RequestHeaderLineType.EMPTY_LINE)) {
                    User user = User.of(IOUtils.readData(br, contentLength), UrlType.of(url));
                    if (user == null &&
                            (UrlType.LOGIN_USER == UrlType.of(url) || UrlType.CREATE_USER == UrlType.of(url))) {
                        break;
                    }
                    isCreateSuccess = createUser(url, user);
                    isLoginSuccess = loginUser(url, user);
                    break;
                }
                if (requestHeaderLineType == RequestHeaderLineType.COOKIE_LINE) {
                    cookie = Cookie.of(line);
                }
                line = br.readLine();
            }

            logger.debug("Path URL : {}", url);
            DataOutputStream dos = new DataOutputStream(out);

            if (UrlType.CREATE_USER == UrlType.of(url)) {
                if (isCreateSuccess)
                    response302Header(dos, "/index.html");
                response302Header(dos, "/user/form.html");
            }
            if (UrlType.LOGIN_USER == UrlType.of(url)) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates"+"/user/login_failed.html");
                if (isLoginSuccess)
                    body = FileIoUtils.loadFileFromClasspath("./templates"+"/index.html");
                response200LoginHeader(dos, body.length, isLoginSuccess);
                responseBody(dos, body);
            }

            if (UrlType.LIST == UrlType.of(url)) {
                if (cookie != null && cookie.isLogined()) {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile("user/list");

                    Map<String, List> users = new HashMap<>();
                    users.put("users", DataBase.findAll().stream().collect(Collectors.toList()));

                    String profilePage = template.apply(users);
                    byte[] body = profilePage.getBytes();
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
                response302Header(dos, "/user/login.html");
            }

            byte[] body = FileIoUtils.loadFileFromClasspath("./templates"+url);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error("IOException : {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception : {}", e.getStackTrace());
        }
    }

    private boolean createUser(String url, User user) {
        if (UrlType.of(url) == UrlType.CREATE_USER &&
                DataBase.findUserById(user.getUserId()) == null) {
            DataBase.addUser(user);
            return true;
        }
        return false;
    }

    private boolean loginUser(String url, User user) {
        if (UrlType.of(url) == UrlType.LOGIN_USER) {
            logger.debug("login user : {}", user.toString());
            User findUser = DataBase.findUserById(user.getUserId());
            logger.debug("findUser : {}", findUser);
            if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
                logger.debug("로그인 성공");
                return true;
            }
        }
        return false;
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

    private void response200LoginHeader(DataOutputStream dos, int lengthOfBodyContent, boolean isLogined) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: " + "logined=" + isLogined + "; path=/" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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
