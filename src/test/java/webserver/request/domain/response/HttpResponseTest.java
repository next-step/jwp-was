//package webserver.request.domain.response;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import webserver.request.domain.response.HttpResponse;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//
//class HttpResponseTest {
//
//    private String testDirectory = "./src/test/resources/";
//
//    @Test
//    @DisplayName("forward 응답이면 응답의 body는 아무것도 없다.")
//    void t() throws FileNotFoundException {
//        HttpResponse response = new HttpResponse(createOutputStream("HTTP_FORWARD.txt"));
//        Assertions.assertThat(response.getBody()).isEqualTo("");
//    }
//
//    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
//        return new FileOutputStream(new File(testDirectory + filename));
//    }
//}