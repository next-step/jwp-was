package webserver.request;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.response.HeaderProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Ignore
public class RequestTest {

    private static final String FILE_PATH_OF_REQUEST = "./src/test/resources/";

    public static Request getRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(getPathName(fileName)));
        return HttpRequest.newInstance(in);
    }

    private static String getPathName(String fileName) {
        return FILE_PATH_OF_REQUEST + fileName;
    }

    @Test
    void request_GET() throws IOException {
        // given
        Request request = getRequest("Request_GET.txt");

        // when & then
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader(HeaderProperty.CONNECTION)).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    void request_POST() throws IOException {
        // given
        Request request = getRequest("Request_POST.txt");

        // when & then
        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader(HeaderProperty.CONTENT_LENGTH)).isEqualTo("46");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}