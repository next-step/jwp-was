package controller;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
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
import utils.IOUtils;
import webserver.RequestLine;

class UserCreateControllerTest {
    private String testDirectory = "./src/test/resources/";

    @DisplayName("회원가입 성공")
    @Test
    void createUserSuccess() throws Exception {
        UserCreateController userCreateController = new UserCreateController();
        HttpRequest httpRequest = createRequest("Http_POST.http");
        userCreateController.service(httpRequest, HttpResponse.init());

        assertThat(DataBase.findAll().size()).isNotZero();
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
