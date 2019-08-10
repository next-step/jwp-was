package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.converter.HttpResponseConverter;
import webserver.http.HttpResponse;
import webserver.http.ServletContainer;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            String content = IOUtils.readData(new BufferedReader(new InputStreamReader(in, "UTF-8")), 1024);

            logger.debug(content);
            HttpResponse response = new ServletContainer().getResponse(content);
            DataOutputStream dos = new DataOutputStream(out);
            responseReq(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void responseReq(DataOutputStream dos, HttpResponse response){
        try {
            byte[] returnContent = Optional.ofNullable(response.getResultBody())
                    .orElse("").getBytes();
            dos.writeBytes(HttpResponseConverter.getResponse(response));
            dos.write(returnContent, 0, returnContent.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
