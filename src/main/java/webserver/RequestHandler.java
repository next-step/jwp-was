package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.StringUtils;
import webserver.http.Cookie;
import webserver.http.HttpHeaders;
import webserver.http.HttpUtils;
import webserver.http.RequestLine;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String INDEX_PAGE = "/index.html";
    public static final String TEMPLATES_PATH = "/templates";
    public static final String LOGIN_FAILED_PAGE = "/user/login_failed.html";

    public static final String COOKIE_LOGIN_NAME = "logined";

    private Socket connection;
    private final HandleBarsViewResolver viewResolver;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.viewResolver = new HandleBarsViewResolver(TEMPLATES_PATH, ".html");
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();

            RequestLine requestLine = RequestLine.parse(line);
            HttpHeaders httpHeaders = readHeader(br);
            Map<String, String> parameters = getParametersByMethod(br, requestLine, httpHeaders);
            Map<String, Cookie> cookies = HttpUtils.parserCookie(httpHeaders.get("Cookie"));

            if ("POST".equals(requestLine.getMethod())
                    && "/user/create".equals(requestLine.getPath())) {
                createUser(out, parameters);
            } else if ("POST".equals(requestLine.getMethod())
                    && "/user/login".equals(requestLine.getPath())) {
                login(out, parameters);
            } else if ("GET".equals(requestLine.getMethod())
                    && "/user/list".equals(requestLine.getPath())) {
                renderUserList(out, requestLine, cookies);
            } else {
                renderHtml(out, requestLine);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void renderUserList(OutputStream out, RequestLine requestLine, Map<String, Cookie> cookies) throws IOException {
        try {
            Cookie logined = cookies.get(COOKIE_LOGIN_NAME);

            if (!Boolean.parseBoolean(logined.getValue())) {
                throw new AuthenticationException();
            }

            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = viewResolver.render(requestLine.getPath(), model).getBytes();
            response200Header(dos, body.length, null);
            responseBody(dos, body);
        } catch(RuntimeException e) {
            DataOutputStream dos = new DataOutputStream(out);
            response302Header(dos, INDEX_PAGE, null);
            responseBody(dos, new byte[]{});
        }
    }

    private void renderHtml(OutputStream out, RequestLine requestLine) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = getContent(requestLine.getPath());
        response200Header(dos, body.length, null);
        responseBody(dos, body);
    }

    private void login(OutputStream out, Map<String, String> parameters) throws IOException, URISyntaxException {
        String userId = parameters.get("userId");
        String password = parameters.get("password");


        Cookie cookie = new Cookie(COOKIE_LOGIN_NAME, "false");
        try {
            User foundUser = Optional.ofNullable(DataBase.findUserById(userId))
                    .orElseThrow(() -> new EntityNotFoundException(userId));

            if (!foundUser.equalToPassword(password)) {
                throw new AuthenticationException();
            };

            cookie.setValue("true");

            logger.info(String.format("Logined %s", userId));
            DataOutputStream dos = new DataOutputStream(out);
            response302Header(dos, INDEX_PAGE, cookie);
            responseBody(dos, new byte[]{});
        } catch (RuntimeException e) {
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = getContent(LOGIN_FAILED_PAGE);
            response200Header(dos, body.length, cookie);
            responseBody(dos, body);
        }
    }

    private void createUser(OutputStream out, Map<String, String> parameters) {
        logger.debug("Create new user");

        User newUser = newUser(parameters);

        DataBase.addUser(newUser);

        DataOutputStream dos = new DataOutputStream(out);
        response302Header(dos, INDEX_PAGE, null);
        responseBody(dos, new byte[]{});
    }

    private HttpHeaders readHeader(BufferedReader br) throws IOException {
        String line;
        HttpHeaders httpHeaders = new HttpHeaders();
        while (!"".equals(line = br.readLine())) {
            if (null == line) break;
            httpHeaders.add(line);
        }
        return httpHeaders;
    }

    private Map<String, String> getParametersByMethod(BufferedReader br, RequestLine requestLine, HttpHeaders httpHeaders) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters = HttpUtils.parseQueryString(requestLine.getQueryString());
        if ("POST".equals(requestLine.getMethod())) {
            int contentLength = Integer.valueOf(httpHeaders.get("Content-Length"));
            if (contentLength > 0) {
                parameters = HttpUtils.parseQueryString(IOUtils.readData(br, contentLength));
            }
        }
        return parameters;
    }

    private User newUser(Map<String, String> parameters) {
        try {
            return new User(
                    parameters.get("userId"),
                    parameters.get("password"),
                    StringUtils.unescape(parameters.get("name")),
                    StringUtils.unescape(parameters.get("email"))
            );
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private byte[] getContent(String path) throws IOException, URISyntaxException {
        Path templatePath = Paths.get(".", TEMPLATES_PATH, path);
        try {
            return FileIoUtils.loadFileFromClasspath(templatePath.toString());
        } catch (NullPointerException e) {
            throw new FileNotFoundException(path);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, Cookie cookie) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            if (cookie != null) {
                dos.writeBytes(cookie.toString()+"\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location, Cookie cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: %s \r\n", location));
            if (cookie != null) {
                dos.writeBytes(cookie.toString()+"\r\n");
            }
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
