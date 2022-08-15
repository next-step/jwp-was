package controller;

import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void 회원가입시_user_정보_저장() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.http"));
        final UserCreateController controller = new UserCreateController();
        final HttpRequest httpRequest = new HttpRequest(in);
        controller.service(httpRequest, new HttpResponse());

        assertThat(DataBase.findAll().size() > 0 ).isTrue();
    }

}
