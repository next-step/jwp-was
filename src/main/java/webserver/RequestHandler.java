package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;
import db.DataBase;
import http.*;
import model.User;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import view.TemplateView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = makeHttpResponse(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);
            dos.write(httpResponse.makeHttpResponseBytes(), 0, httpResponse.makeHttpResponseBytes().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private HttpResponse makeHttpResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        RequestLine requestLine = httpRequest.getRequestLine();

        // TODO MVC
        String filePath = makeFilePath(requestLine);
        String path = requestLine.getPath();
        if ("/user/create".equalsIgnoreCase(path)) {
            DataBase.addUser(User.newInstance(httpRequest.getQueryMap()));
            return HttpResponse.redirectBy302StatusCode("/index.html");
        }

        if ("/user/login".equalsIgnoreCase(path)) {
            Map<String, String> additionalHeaders = Maps.newHashMap();
            if (isAuthuorizedUser(httpRequest)) {
                additionalHeaders.put("Set-Cookie", "logined=true; Path=/");
                return HttpResponse.redirectBy302StatusCode("/index.html", additionalHeaders);
            }

            additionalHeaders.put("Set-Cookie", "logined=false; Path=/");
            return HttpResponse.redirectBy302StatusCode("/user/login_fail.html", additionalHeaders);
        }

        if ("/user/list.html".equalsIgnoreCase(path)) {
            if (isAuthuorizedUser(httpRequest)) {
                Users users = new Users();
                users.addUseraAll(DataBase.findAll());

                String templateViewString = readTemplateView("user/list", users);
                return HttpResponse.from(templateViewString .getBytes(), HttpStatus.OK);
            }

            return HttpResponse.redirectBy302StatusCode("/user/login.html");
        }

        return HttpResponse.from(filePath, HttpStatus.OK);
    }

    private String readTemplateView(String filePath, Object context) {
        TemplateView templateView = new TemplateView();
        return templateView.read(filePath, context);
    }

    private boolean isAuthuorizedUser(@Nonnull HttpRequest httpRequest) {
        return isAuthuorizedUserByCookie(httpRequest) || isAuthuorizedUserByDataBase(httpRequest);
    }

    private boolean isAuthuorizedUserByCookie(@Nonnull HttpRequest httpRequest) {
        String cookie = httpRequest.getHeaders().get("Cookie");
        if (StringUtils.isEmpty(cookie)) {
            return false;
        }

        String[] splitByEqualSign = cookie.split("=");
        if (splitByEqualSign.length < 2) {
            return false;
        }

        if ("logined".equalsIgnoreCase(splitByEqualSign[0]) && "true".equalsIgnoreCase(splitByEqualSign[1])) {
            return true;
        }

        return false;
    }

    private boolean isAuthuorizedUserByDataBase(@Nonnull HttpRequest httpRequest) {
        String userId = (String)httpRequest.getQueryMap().get("userId");
        String password = (String)httpRequest.getQueryMap().get("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            return false;
        }

        return user.isAuthuorizedUser(userId, password);
    }

    @Nonnull
    private String makeFilePath(@Nullable RequestLine requestLine) {
        if (requestLine != null) {
            String path = requestLine.getPath();
            String filePath;
            if ("/".equalsIgnoreCase(requestLine.getPath())) {
                path = "/index.html";
            }

            if (path.endsWith(".html") || path.endsWith(".ico")) {
                filePath = "./templates" + path;
                logger.debug("filePath : " + filePath);
                return filePath;
            }

            filePath = "./static" + path;
            logger.debug("filePath : " + filePath);

            return filePath;
        }

        return "";
    }
}
