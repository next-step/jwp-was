package webserver;

import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.BufferedReaderToHttpRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);

			HttpRequest httpRequest = bufferedReaderToHttpRequest.parsed();
			HttpResponse httpResponse = new HttpResponse(out);

			String path = httpRequest.getPath();
            logger.debug("path : {}", path);

            if (path.endsWith(".html")) {
                processPageRequest(httpResponse, path);
            } else if (containsStaticPath(path)) {
                processStaticRequest(httpResponse, path);
            } else if (path.startsWith("/user/create")) {
				new CreateUserController().service(httpRequest, httpResponse);
            } else if (path.startsWith("/user/login")) {
                new LoginController().service(httpRequest, httpResponse);
            } else if (path.startsWith("/user/list")) {
                new ListUserController().service(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processPageRequest(HttpResponse httpResponse, String path) throws IOException, URISyntaxException {
        path = "./templates" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
		httpResponse.response200Header(body.length);
		httpResponse.responseBody(body);
    }

    private boolean containsStaticPath(String path) {
        return path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/font") || path.startsWith("/images");
    }

    private void processStaticRequest(HttpResponse httpResponse, String path) throws IOException, URISyntaxException {
        path = "./static" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        if (path.startsWith("./static/css")) {
			httpResponse.response200CssHeader(body.length);
        } else {
			httpResponse.response200Header(body.length);
        }
		httpResponse.responseBody(body);
    }
}
