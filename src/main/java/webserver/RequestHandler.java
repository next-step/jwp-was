package webserver;

import model.controller.View;
import model.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String DEFAULT_RESOURCE_EXTENSION = ".html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = response(httpRequest).orElseThrow(() -> new NoRouteToHostException("test"));
            writeResponseIntoOutputStream(httpResponse, out);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private boolean writeResponseIntoOutputStream(HttpResponse httpResponse, OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes(httpResponse.print());
            dos.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private Optional<HttpResponse> response(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        Optional<byte[]> body = ResourceFinder.find(requestLine.getPath());

        if (body.isPresent()) {
            return Optional.of(getOkResponse(body.get(), false));
        }

        Optional<Method> method = ControllerFinder.findController(requestLine);

        if (method.isPresent()) {
            View view;
            try {
                view = returnResourcePath(httpRequest, method.get());
            } catch (Exception e) {
                return Optional.empty();
            }

            if (view.isRedirect()) {
                return Optional.of(getRedirectResponse(view.getResourcePath(), view.isHasLoginCookie()));
            }
            body = getBodyByControllerResource(view.getResourcePath());
            return Optional.of(getOkResponse(body.get(), view.isHasLoginCookie()));
        }
        return Optional.empty();
    }

    private Optional<byte[]> getBodyByControllerResource(String returnResourcePath) {
        return ResourceFinder.find(UriPath.of(returnResourcePath + DEFAULT_RESOURCE_EXTENSION));
    }

    private View returnResourcePath(HttpRequest httpRequest, Method method) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return ControllerMethodInvoker.invoke(method, httpRequest);
    }

    private HttpResponse getOkResponse(byte[] body, boolean hasLoginCookie) {
        return HttpResponse.of(getOkResponseHeader(body.length, hasLoginCookie), body);
    }

    private HttpResponse getRedirectResponse(String location, boolean hasLoginCookie) {
        return HttpResponse.of(getRedirectResponseHeader(location, hasLoginCookie));
    }

    private HttpResponseHeader getOkResponseHeader(int lengthOfBodyContent, boolean hasLoginCookie) {
        HttpResponseHeader httpResponseHeader = HttpResponseHeader.of(StatusLine.of(StatusCode.OK))
                .putAttribute("Content-Type", "text/html;charset=utf-8")
                .putAttribute("Content-Length", Integer.toString(lengthOfBodyContent));

        if (hasLoginCookie) httpResponseHeader.putAttribute("Set-Cookie", "logined=true; Path=/");

        return httpResponseHeader;
    }

    private HttpResponseHeader getRedirectResponseHeader(String location, boolean hasLoginCookie) {
        HttpResponseHeader httpResponseHeader = HttpResponseHeader.of(StatusLine.of(StatusCode.FOUND))
                .putAttribute("Location", location);

        if (hasLoginCookie) httpResponseHeader.putAttribute("Set-Cookie", "logined=true; Path=/");

        return httpResponseHeader;
    }
}
