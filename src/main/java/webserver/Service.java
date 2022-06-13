package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.Protocol;
import webserver.response.HttpStatus;
import webserver.response.Response;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeader;

public class Service {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final DataOutputStream dos;

    public Service(DataOutputStream dos) {
        this.dos = dos;
    }

    public void helloWorld() {
        ResponseHeader responseHeader = new ResponseHeader(
                Protocol.HTTP_1_1, HttpStatus.OK
        ).setContentType("text/html;charset=utf-8");
        ResponseBody responseBody = new ResponseBody("Hello World");
        Response response = new Response(responseHeader, responseBody);
        try {
            dos.writeBytes(response.toString());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
