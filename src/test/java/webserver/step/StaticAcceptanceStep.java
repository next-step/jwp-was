package webserver.step;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import webserver.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StaticAcceptanceStep {

    public static ExtractableResponse<Response> requestStaticFile(String staticFilePath) {
        return RestAssured.given().log().all()
                .contentType(ContentType.ANY)
                .when().get(staticFilePath)
                .then().log().all()
                .extract();
    }

    public static void retrieveFile(ExtractableResponse<Response> response, String contentType) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.header(HttpHeaders.CONTENT_TYPE)).contains(contentType)
        );
    }
}
