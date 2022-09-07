package controller;

import db.DataBase;
import model.http.HttpHeader;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.request.RequestBody;
import org.junit.jupiter.api.Test;
import utils.IOUtils;
import webserver.RequestLine;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void 회원가입시_user_정보_저장() throws Exception {
        final UserCreateController controller = new UserCreateController();
        final HttpRequest httpRequest = createRequest("Http_POST.http");
        controller.service(httpRequest, new HttpResponse());

        assertThat(DataBase.findAll().size() > 0 ).isTrue();
    }

    private HttpRequest createRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        final RequestLine requestLine = new RequestLine(IOUtils.readRequestData(bufferedReader));
        final HttpHeader httpHeader = new HttpHeader(IOUtils.readHeaderData(bufferedReader));
        final RequestBody body = new RequestBody(IOUtils.readData(bufferedReader, httpHeader.getValueToInt("Content-Length")));

        return new HttpRequest(httpHeader, requestLine, body);
    }
}
