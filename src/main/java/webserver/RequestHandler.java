package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.Controller;
import controller.UserController;
import exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.*;
import webserver.http.HttpVersion;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Handlebars handlebars;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.handlebars = new Handlebars(classPathTemplateLoader());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(inputStream);
            HttpResponse response = new HttpResponse(HttpVersion.HTTP_1_1);

            View view;
            if (isStaticFile(request.path())) {
                view = new StaticViewResolver().toView(response, request.path(), callbackStaticView());
            } else {
                Controller userController = UserController.getInstance();
                ModelAndView modelAndView = userController.get(request, response);

                view = new CallbackViewResolver().toView(response, modelAndView, callbackHandlebarsView(modelAndView));
            }
            Objects.requireNonNull(view, "view 객체를 생성하지 못했습니다.");

            response.flush(outputStream, view.responseBody());

        } catch (FileNotFoundException e) {
            logger.error("File not found : " + e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    private TemplateLoader classPathTemplateLoader() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        return loader;
    }

    // todo 리팩토링 할 때 다른 클래스로 분리할 것
    private boolean isStaticFile(final String path) {
        return Stream.of(".css", ".js", ".png", ".ico", ".eot", ".svg", ".ttf", ".woff", ".woff2")
                .anyMatch(suffix -> StringUtils.endsWithIgnoreCase(path, suffix));
    }

    private Function<String, View> callbackStaticView() {
        return path -> {
            try {
                return new StaticView(path);
            } catch (IOException | URISyntaxException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            return null;
        };
    }

    private Function<String, View> callbackHandlebarsView(final ModelAndView modelAndView) {
        return path -> {
            try {
                return new HandlebarsView(handlebars, path, modelAndView);
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            return null; // lambda 예외처리는 어떻게 처리하는게 베스트 프랙티스일까요?
        };
    }
}
