package webserver;

import java.io.*;
import java.net.Socket;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import webserver.request.Request;
import webserver.request.RequestApiPath;
import webserver.response.FileResponse;
import webserver.response.Response;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String REDIRECT = "redirect:";
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            DataOutputStream dos = new DataOutputStream(out);
            Request request = IOUtils.convertRequest(in);
            logger.debug("requestLine : {}", request.getRequestLine());
            Response response = getResponse(request);
            response.sendStatus(dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response getResponse(Request request) {
        String requestPath = request.getRequestPath();
        logger.debug("webserver.request Path : {}", requestPath);
        Response response = new Response();
        return FileResponse.getFileResponse(requestPath)
                .orElse(getApiResponse(request, response));
    }

    private Response getApiResponse(Request request, Response response) {

        String path = RequestApiPath.getViewName(request, response);
        return getResponse(request, response, path);
    }

    private Response getResponse(Request request, Response response, String path) {
        if (path.startsWith(REDIRECT)) {
            return redirectResponse(response, path);
        }

        try {
            response.makeStatus(HttpStatus.OK);
            response.addBody(ViewResolver.mapping(response, path).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private Response redirectResponse(Response response, String path) {
        response.makeStatus(HttpStatus.FOUND);
        response.makeLocationPath(path.replace(REDIRECT, StringUtils.EMPTY));
        return response;
    }
}
