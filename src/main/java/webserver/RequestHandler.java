package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.request.ContentType;
import webserver.request.RequestBody;
import webserver.request.RequestHeaders;
import webserver.request.RequestLine;
import webserver.request.UserBinder;
import webserver.response.ResponseHeaders;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            final DataOutputStream dos = new DataOutputStream(out);

            final RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
            final RequestHeaders requestHeaders = new RequestHeaders();
            addAllRequestHeaders(bufferedReader, requestHeaders);
            final RequestBody requestBody = getRequestBody(bufferedReader, requestHeaders);

            if (requestForCreateUser(requestLine)) {
                createUser(requestBody, dos);
                return;
            }

            if (requestForLogin(requestLine)) {
                login(requestBody, dos);
                return;
            }

            if (requestForUserList(requestLine)) {
                userList(requestHeaders, dos);
                return;
            }

            String fileExtension = FileIoUtils.getFileExtension(requestLine.getLocation());
            final String contentType = ContentType.of(fileExtension).getContentType();
            String filePath = getFilePath(fileExtension);

            final byte[] body = FileIoUtils.loadFileFromClasspath(filePath + requestLine.getLocation());

            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String getFilePath(final String fileExtension) {
        if (fileExtension.endsWith("html") || fileExtension.endsWith("ico")) {
            return "templates";
        }
        return "static";
    }

    private void userList(final RequestHeaders requestHeaders, final DataOutputStream dos) throws IOException {
        final boolean login = Optional.ofNullable(requestHeaders.get("Cookie"))
            .map(logined -> logined.equals("logined=true"))
            .orElse(false);
        if (!login) {
            response302Header(dos, "/user/login.html");
            return;
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (context, options) -> (int) context + 1);

        Template template = handlebars.compile("user/list");

        final Collection<User> users = DataBase.findAll();
        final Map<String, Collection<User>> param = Map.of("users", users);

        String profilePage = template.apply(param);
        final byte[] body = profilePage.getBytes();

        response200Header(dos, body.length, "text/html");
        responseBody(dos, body);

    }

    private boolean requestForUserList(final RequestLine requestLine) {
        return requestLine.isGet() && requestLine.getLocation().equals("/user/list");
    }

    private void addAllRequestHeaders(final BufferedReader bufferedReader, final RequestHeaders requestHeaders) throws IOException {
        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            logger.debug("header : {}", header);
            requestHeaders.add(header);
            header = bufferedReader.readLine();
        }
    }

    private RequestBody getRequestBody(final BufferedReader bufferedReader, final RequestHeaders requestHeaders) throws IOException {
        if (requestHeaders.hasRequestBody()) {
            final String body = IOUtils.readData(bufferedReader, requestHeaders.getContentLength());
            logger.debug("body : {}", body);
            return new RequestBody(body);
        }
        return RequestBody.EMPTY_REQUEST_BODY;
    }

    private boolean requestForLogin(final RequestLine requestLine) {
        return requestLine.isPost() && requestLine.getLocation().equals("/user/login");
    }

    private void login(final RequestBody requestBody, final DataOutputStream dos) {
        User user = UserBinder.from(requestBody.getParameters());
        logger.debug("user = {}", user);

        final boolean loginSuccess = login(user);
        logger.debug("loginSuccess = {}", loginSuccess);

        final ResponseHeaders responseHeaders = new ResponseHeaders();
        if (loginSuccess) {
            responseHeaders.add("Location", "/index.html");
            responseHeaders.add("Set-Cookie", "logined=true; Path=/");
            response302Header(dos, responseHeaders);
            return;
        }
        responseHeaders.add("Location", "/user/login_failed.html");
        responseHeaders.add("Set-Cookie", "logined=false; Path=/");
        response302Header(dos, responseHeaders);
    }

    private boolean login(final User user) {
        return Optional.ofNullable(DataBase.findUserById(user.getUserId()))
            .map(it -> it.getPassword().equals(user.getPassword()))
            .orElse(false);
    }

    private void createUser(final RequestBody requestBody, final DataOutputStream dos) {
        User user = UserBinder.from(requestBody.getParameters());
        logger.debug("user = {}", user);

        DataBase.addUser(user);
        response302Header(dos, "/index.html");
    }

    private boolean requestForCreateUser(final RequestLine requestLine) {
        return requestLine.isPost() && requestLine.getLocation().equals("/user/create");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, final String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, final String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, final ResponseHeaders responseHeaders) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            for (final String header : responseHeaders.getHeaders().keySet()) {
                dos.writeBytes(header + ": " + responseHeaders.get(header) + "\r\n");
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
