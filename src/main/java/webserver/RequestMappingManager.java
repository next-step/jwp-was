package webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponse200;
import http.response.HttpResponse302;
import http.response.HttpResponse400;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestMappingManager.class);

    private final static String PHYSICAL_DEFAULT_PATH = "./templates";

    private final Map<String, String> requestMap = new HashMap<>();

    public static byte[] fileLoadFromPath(String uri) {
        try {
            String path = PHYSICAL_DEFAULT_PATH.concat(uri);
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new byte[0];
    }

    private static <T> T convertMapToObject(Map<String, String> params, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        T object = mapper.convertValue(params, toValueType);
        return object;
    }

    public static void execute(HttpRequest httpRequest, DataOutputStream dos) {
        HttpResponse httpResponse;

        byte[] body = new byte[0];
        logger.debug("request url: {} - cookie{}", httpRequest.getPath(), httpRequest.getHeader("Cookie"));
        switch (httpRequest.getPath()) {
            case "/user/create":
                User user = convertMapToObject(httpRequest.getParameters(), User.class);
                UserController.create(user);
                body = user.toString().getBytes();

                httpResponse = new HttpResponse302("http://localhost:8080/index.html", "1.1");
                break;
            case "/user/login":

                User user1 = convertMapToObject(httpRequest.getParameters(), User.class);
                boolean isLogined = UserController.login(user1);

                if (isLogined) {
                    httpResponse = new HttpResponse302("http://localhost:8080/index.html", "1.1");
                    httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                } else {
                    httpResponse = new HttpResponse302("http://localhost:8080/user/login_failed.html", "1.1");
                }
                break;
            case "/user/list":
                logger.debug("cookie{}", httpRequest.getHeader("Cookie"));

                try {
                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile("user/list");

                    User user2 = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
                    String profilePage = template.apply(user2);

                    httpResponse = new HttpResponse200("1.1");
                    body = profilePage.getBytes();
                } catch (Exception ex) {
                    httpResponse = new HttpResponse200("1.1");
                }
                break;
            default:
                body = RequestMappingManager.fileLoadFromPath(httpRequest.getPath());

                if (body.length > 0) {
                    httpResponse = new HttpResponse200("1.1");
                } else {
                    httpResponse = new HttpResponse400("1.1");
                }
                break;
        }
        try {
            logger.debug("response: {}", httpResponse.getResponseHeader());
            dos.writeBytes(httpResponse.getResponseHeader());

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
