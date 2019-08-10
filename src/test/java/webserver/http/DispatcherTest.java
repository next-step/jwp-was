package webserver.http;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.stub.StubHttpRequest;
import webserver.http.stub.StubHttpResponse;
import webserver.http.resource.StubResourceHandler;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.FileIoUtils.loadFile;
import static webserver.http.HttpStatusCode.*;

class DispatcherTest {

    private static final String EXPECTED_LOCATION = "/index.html";
    private static final String EXPECTED_ERROR_LOCATION = "/error.html";

    private File file;
    private DataOutputStream dos;
    private HttpResponse response;
    private HttpRequest request;
    private ViewResolver viewResolver;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        this.file = new File("test.txt");
        this.dos = new DataOutputStream(new FileOutputStream(file));
        this.response = new StubHttpResponse(dos);
        this.request = new StubHttpRequest(EXPECTED_LOCATION);
        this.viewResolver = ViewResolver.from(request, response);
        viewResolver.setResourceHandler(new StubResourceHandler());
    }

    @Test
    void notFound() throws IOException {
        viewResolver.error(NOT_FOUND);
        assertThat(new String(loadFile(file)))
                .contains(String.valueOf(NOT_FOUND.getCode()), EXPECTED_ERROR_LOCATION);
    }

    @Test
    void forward() throws IOException {
        viewResolver.forward(EXPECTED_LOCATION);
        assertThat(new String(loadFile(file)))
                .contains(String.valueOf(OK.getCode()), EXPECTED_LOCATION);
    }

    @Test
    void redirect() throws IOException {
        viewResolver.redirect(EXPECTED_LOCATION);
        assertThat(new String(loadFile(file)))
                .contains(String.valueOf(FOUND.getCode()), EXPECTED_LOCATION);
    }

    @AfterEach
    void exit() {
        try {
            dos.close();
        } catch (IOException ignore) {}
    }

}
