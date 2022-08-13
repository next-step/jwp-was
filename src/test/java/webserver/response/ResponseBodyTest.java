package webserver.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class ResponseBodyTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseBodyTest() throws FileNotFoundException {
        InputStream in = new FileInputStream(testDirectory + "HTML.txt");
        String value = in.toString();

        ResponseBody responseBody = ResponseBody.parse(value);

        Assertions.assertThat(responseBody.getBody()).isEqualTo(value.getBytes());
    }
}