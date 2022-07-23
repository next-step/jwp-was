package webserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Request;
import webserver.http.Response;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    private final List<Handler> handlers = List.of(
            new ResourceHandler(),
            new CreateMemberHandler()
    );

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = Request.parseOf(readRequest(in));
            logger.debug("request:{} ", request);

            Response response = new Response();

            for (Handler handler : handlers) {
                response = getResponse(request, response, handler);
            }

            writeResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response getResponse(Request request, Response response, Handler handler) {
        if (handler.isSupport(request)) {
            response = handler.handle(request);
        }
        return response;
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

    private List<String> readRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        List<String> requestLines = new ArrayList<>();

        String line = br.readLine();
        requestLines.add(line);

        while (line != null && !"".equals(line)) {
            line = br.readLine();
            requestLines.add(line);
        }

        return requestLines;
    }
}
