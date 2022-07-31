package webserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;;
import webserver.handler.*;
import webserver.http.*;
import webserver.view.View;
import webserver.view.ViewResolver;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    private final HandlerMapping handlerMapping = new HandlerMapping(
            Map.of(
                    new RequestMappingInfo("/user/create", HttpMethod.POST), new CreateMemberHandler(),
                    new RequestMappingInfo("/user/list", HttpMethod.GET), new ListMemberHandler(),
                    new RequestMappingInfo("/user/login", HttpMethod.POST), new LoginMemberHandler(),
                    new RequestMappingInfo("/.*", HttpMethod.GET), new StaticFileHandler()
            )
    );

    private final ViewResolver viewResolver = new ViewResolver();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = readRequest(new BufferedReader(new InputStreamReader(in)));
            logger.debug("request:{} ", request);

            Response response = new Response();
            handleRequest(request, response);
            logger.debug("response:{} ", response);

            writeResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(Request request, Response response) {
        Handler handler = handlerMapping.getHandler(request);

        ModelAndView modelAndView = handler.handle(request, response);

        if (modelAndView == null) {
            return;
        }

        View view = viewResolver.resolveView(modelAndView.getView());

        view.render(modelAndView.getModel(), response);
    }

    private void writeResponse(OutputStream out, Response response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        for (String message : response.getMessages()) {
            dos.writeBytes(message + "\r\n");
        }

        dos.writeBytes("\r\n");

        if (response.hasBody()) {
            dos.write(response.getBody(), 0, response.getBody().length);
        }

        dos.flush();
    }

    private Request readRequest(BufferedReader reader) throws IOException {
        RequestLine requestLine = RequestLine.parseOf(reader.readLine());

        Headers headers = readHeaders(reader);

        String requestBody = readRequestBody(reader, headers);

        return new Request(requestLine, headers, requestBody);
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
