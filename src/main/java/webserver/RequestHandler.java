package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.handler.CreateMemberHandler;
import webserver.handler.ListMemberHandler;
import webserver.handler.LoginMemberHandler;
import webserver.handler.StaticFileHandler;
import webserver.http.*;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    private final StaticLocationProvider staticLocationProvider = new StaticLocationProvider(
            List.of("./templates", "./static")
    );

    private final HandlerMapping handlerMapping = new HandlerMapping(List.of(
            new RequestMappingRegistration("/user/create", HttpMethod.POST, new CreateMemberHandler()),
            new RequestMappingRegistration("/user/list", HttpMethod.GET, new ListMemberHandler()),
            new RequestMappingRegistration("/user/login", HttpMethod.POST, new LoginMemberHandler()),
            new RequestMappingRegistration("/.*", HttpMethod.GET, new StaticFileHandler(staticLocationProvider))
    ));

    private final ViewResolver viewResolver = new ViewResolver("/templates", ".html");

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = readRequest(new BufferedReader(new InputStreamReader(in)));
            logger.debug("request:{} ", httpRequest);

            HttpResponse httpResponse = new HttpResponse();
            handleRequest(httpRequest, httpResponse);
            logger.debug("response:{} ", httpResponse);

            writeResponse(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        Handler handler = handlerMapping.getHandler(httpRequest);

        ModelAndView modelAndView = handler.handle(httpRequest, httpResponse);

        if (modelAndView == null) {
            return;
        }

        View view = viewResolver.resolveView(modelAndView.getView());

        view.render(modelAndView.getModel(), httpResponse);
    }

    private void writeResponse(OutputStream out, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        for (String message : httpResponse.getMessages()) {
            dos.writeBytes(message + "\r\n");
        }

        dos.writeBytes("\r\n");

        if (httpResponse.hasBody()) {
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
        }

        dos.flush();
    }

    private HttpRequest readRequest(BufferedReader reader) throws IOException {
        RequestLine requestLine = RequestLine.parseOf(reader.readLine());

        Headers headers = readHeaders(reader);

        String requestBody = readRequestBody(reader, headers);

        return new HttpRequest(requestLine, headers, requestBody);
    }

    private Headers readHeaders(BufferedReader reader) throws IOException {
        List<String> headerLines = new ArrayList<>();

        String line = reader.readLine();
        headerLines.add(line);

        while (line != null && !"".equals(line)) {
            line = reader.readLine();
            headerLines.add(line);
        }

        return Headers.parseOf(headerLines);
    }

    private String readRequestBody(BufferedReader reader, Headers headers) throws IOException {
        String value = headers.getValue("Content-Length");

        if (value != null && !value.equals("")) {
            return IOUtils.readData(reader, Integer.parseInt(value));
        }

        return "";
    }

}
