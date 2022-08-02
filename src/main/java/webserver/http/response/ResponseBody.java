package webserver.http.response;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class ResponseBody {

    private final byte[] body;

    private static final Logger logger = LoggerFactory.getLogger(ResponseBody.class);

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody fromView(String filePath) {
        byte[] body = {};
        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
        return new ResponseBody(body);
    }

    public static ResponseBody fromDynamicView(String template){
        return new ResponseBody(template.getBytes(StandardCharsets.UTF_8));
    }

    public byte[] getBody() {
        return body;
    }
}
