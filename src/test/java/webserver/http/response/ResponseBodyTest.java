package webserver.http.response;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ExecutorsTest;

public class ResponseBodyTest {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    @Test
    void fromFile() {
        final String filePath = "templates/index.html";
        final ResponseBody body = ResponseBody.fromFile(filePath);

        byte[] bytes = body.getBody();

        logger.debug(new String(bytes));
    }

    @Test
    void fromDynamicView() {
        final String dynamicView = "<html>jiwon</html>";
        final ResponseBody body = ResponseBody.fromDynamicView(dynamicView);

        byte[] bytes = body.getBody();

        logger.debug(new String(bytes));
    }
}
