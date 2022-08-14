package webserver.http.response;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ResponseBody {

    private final byte[] body;

    private static final String BLANK_LINE = "\r\n";
    private static final Logger logger = LoggerFactory.getLogger(ResponseBody.class);

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody fromFile(String filePath) {
        byte[] body = {};
        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
        return new ResponseBody(body);
    }

    public static ResponseBody fromDynamicView(String template) {
        return new ResponseBody(template.getBytes(StandardCharsets.UTF_8));
    }

    public void write(final DataOutputStream dos, final byte[] body) {
        try {
            dos.writeBytes(BLANK_LINE);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public byte[] getBody() {
        return body;
    }
}
