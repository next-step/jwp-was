package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.PathMapper;
import webserver.http.RequestLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private static final Map<String, String> responseStatus;
    static {
        responseStatus = new HashMap<>();
        responseStatus.put("/user/create", "302");
    }

    public static void response(OutputStream out, RequestLine requestLine) throws IOException, URISyntaxException {
        DataOutputStream doc = new DataOutputStream(out);

        if (responseStatus.containsKey(requestLine.getPath().getPath())) {
            response302Header(doc, PathMapper.findByKey(requestLine.getPath().getPath()));
            return ;
        }

        byte[] body = FileIoUtils.loadFileFromClasspath(requestLine.getFilePath());
        response200Header(doc, body.length);
        responseBody(doc, body);
    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void response302Header(DataOutputStream dos, String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
