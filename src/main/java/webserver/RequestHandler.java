package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
            request.forEach(logger::debug);

            HttpHeader httpHeader = HttpHeader.from(request);
            boolean logined = httpHeader.isSetCookie();

            int contentLength = httpHeader.getContentLength();
            HttpParameter httpBody = HttpParameter.from(IOUtils.readData(br, contentLength));

            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            String path = requestLine.getUri().getPath();

            if ("/user/login".equals(path)) {
                User user = DataBase.findUserById(httpBody.get("userId"));

                if (Objects.nonNull(user) && user.getPassword().equals(httpBody.get("password"))) {
                    httpResponse.responseRedirectSetCookie("/index.html", true);
                    return;
                }

                httpResponse.responseRedirectSetCookie("/user/login_failed.html", false);
                return;
            }

            if ("/user/create".equals(path)) {
                DataBase.addUser(new User(httpBody.get("userId"), httpBody.get("password"), httpBody.get("name"), httpBody.get("email")));
                DataBase.findAll().forEach(user -> logger.debug("{}", user));

                httpResponse.responseRedirect("/index.html");
                return;
            }

            if ("/user/list".equals(path)) {
                if (!logined) {
                    httpResponse.responseRedirectSetCookie("/user/login_failed.html", false);
                    return;
                }

                Collection<User> users = DataBase.findAll();
                Map<String, Collection<User>> collectionMap = Map.of("users", users);

                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");

                Handlebars handlebars = new Handlebars(loader);
                handlebars.registerHelper("inc", (context, options) -> (int) context + 1);

                Template template = handlebars.compile("user/list");
                String page = template.apply(collectionMap);

                httpResponse.responseOkBody(page, true);
                return;
            }

            httpResponse.responseOk(path, logined);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
