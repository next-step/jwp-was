package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;
import webserver.Response;
import webserver.request.RequestTest;
import webserver.response.HttpResponse;

import java.io.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserCreateControllerTest {

    private Request request;
    private final UserCreateController userCreateController = new UserCreateController();

    private static final String FILE_PATH_OF_RESPONSE = "./src/test/resources/";

    @BeforeEach
    void setUp() throws IOException {
        request = RequestTest.getRequest("Request_CreateUser.txt");
    }

    @DisplayName("회원가입 uri 맞는지 확인")
    @Test
    void isMapping_success() {
        // when
        boolean mappingResult = userCreateController.isMapping(request);

        // then
        assertThat(mappingResult).isTrue();
    }

    @DisplayName("회원가입에 성공 후 main 페이지로 이동")
    @Test
    void service_success() throws Exception {
        // when
        userCreateController.service(request, createResponse("Response_CreateUser.txt"));
    }

    static Response createResponse(String fileName) throws FileNotFoundException {
        return new HttpResponse(createOutputStream(fileName));
    }

    static OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(FILE_PATH_OF_RESPONSE + filename));
    }
}