package webserver.step;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import webserver.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TemplateAcceptanceStep {

    public static ExtractableResponse<Response> requestTemplatePage(String path) {
        return RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static void succeedRendering(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.header(HttpHeaders.CONTENT_TYPE)).contains("text/html;charset=utf-8")
        );
    }
}
