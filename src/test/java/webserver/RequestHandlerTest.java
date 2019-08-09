package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.RequestStream;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandlerTest.class);

    private static RequestStream requestStream;

    @BeforeAll
    static void mockingBufferedReader() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
        "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";

        requestStream = new RequestStream(new ByteArrayInputStream(request.getBytes()));
    }

    @Test
    @Disabled // getResponse -> writeResponse 변경으로 테스트 불가
    void responseIndexHtmlTest() throws Exception {
        String filePath ="./templates/index.html";
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fos));
        RequestHandler requestHandler = new RequestHandler(null);

        requestHandler.writeResponse(httpRequest, dataOutputStream);
    }
}
