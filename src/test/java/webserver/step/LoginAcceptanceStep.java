package webserver.step;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import webserver.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginAcceptanceStep {

    public static ExtractableResponse<Response> requestLogin(String body) {
        return RestAssured.given().log().all()
                .redirects().follow(false)
                .contentType(ContentType.URLENC)
                .body(body)
                .when().post("/user/login")
                .then().log().all()
                .extract();
    }

    public static void redirectIndexWithCookie(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).contains("/index.html"),
                () -> assertThat(response.cookie("logined")).isEqualTo("true")
        );
    }

    public static void redirectLoginFailedWithCookie(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).contains("/user/login_failed.html"),
                () -> assertThat(response.cookie("logined")).isEqualTo("false")
        );
    }
}
