package controller.user;

import http.request.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class IntegrationControllerTest {

    private static final String testDirectory = "./src/test/resources/integrationtest";

    protected HttpRequest createHttpRequestFrom(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + fileName));
        return HttpRequest.from(inputStream);
    }

    protected ByteArrayOutputStream createByteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }

    protected String toStringFrom(ByteArrayOutputStream byteArrayOutputStream) {
        return new String(byteArrayOutputStream.toByteArray());
    }
}
