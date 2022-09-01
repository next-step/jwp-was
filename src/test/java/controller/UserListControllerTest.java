package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.HttpHeaders;
import model.request.HttpRequest;
import model.request.RequestBody;
import model.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestLine;

class UserListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
    String testDirectory = "./src/test/resources/";

    @DisplayName("로그인한 상태가 아니라면 로그인 페이지로 리다이렉트")
    @Test
    void notLoginRedirect() throws Exception {
        HttpRequest httpRequest = createRequest("Http_GET.http");
        UserListController userListController = new UserListController();
        HttpResponse httpResponse = createHttpResponse();
        userListController.service(httpRequest, httpResponse);

        logger.debug("response: {}", httpResponse);
    }

    private HttpResponse createHttpResponse() {
        return HttpResponse.init();
    }

    private HttpRequest createRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        RequestLine requestLine = (new RequestLine(br.readLine())).parse();
        HttpHeaders httpHeaders = new HttpHeaders(String.join("\n", IOUtils.readHeaderData(br)));
        RequestBody requestBody = new RequestBody(br, httpHeaders);

        return new HttpRequest(httpHeaders, requestLine, requestBody, null);
    }
}
