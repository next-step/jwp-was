package webserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.handler.CreateMemberHandler;
import webserver.handler.ListMemberHandler;
import webserver.handler.LoginMemberHandler;
import webserver.handler.ResourceHandler;
import webserver.http.Headers;
import webserver.http.Request;
import webserver.http.RequestLine;
import webserver.http.Response;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    private final List<Handler> handlers = List.of(
            new ResourceHandler(),
            new CreateMemberHandler(),
            new LoginMemberHandler(),
            new ListMemberHandler()
    );

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = readRequest(new BufferedReader(new InputStreamReader(in)));

            logger.debug("request:{} ", request);

            Response response = handlerRequest(request);

            logger.debug("response:{} ", response);

            writeResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response handlerRequest(Request request) {
        return handlers.stream()
                .filter(handler -> handler.isSupport(request))
                .findAny()
                .orElseThrow()
                .handle(request);
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

        Headers headers = getHeaders(reader);

        String requestBody = getRequestBody(reader, headers);

        return new Request(requestLine, headers, requestBody);
    }

    private Headers getHeaders(BufferedReader reader) throws IOException {
        List<String> headerLines = new ArrayList<>();

        String line = reader.readLine();
        headerLines.add(line);

        while (line != null && !"".equals(line)) {
            line = reader.readLine();
            headerLines.add(line);
        }

        return Headers.parseOf(headerLines);
    }

    private String getRequestBody(BufferedReader reader, Headers headers) throws IOException {
        String value = headers.getValue("Content-Length");

        if (value != null && !value.equals("")) {
            return IOUtils.readData(reader, Integer.parseInt(value));
        }

        return "";
    }

}
