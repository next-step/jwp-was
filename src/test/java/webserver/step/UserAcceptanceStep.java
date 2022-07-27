package webserver.step;

import db.DataBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import webserver.http.HttpHeaders;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserAcceptanceStep {

    public static ExtractableResponse<Response> requestUserRegister(String body) {
        return RestAssured.given().log().all()
                .contentType(ContentType.URLENC)
                .body(body)
                .when().post("/user/create")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestUserList(Map<String, String> cookies) {
        return RestAssured.given().log().all()
                .redirects().follow(false)
                .cookies(cookies)
                .when().get("/user/list")
                .then().log().all()
                .extract();
    }

    public static void registeredUserAndRedirectIndex(ExtractableResponse<Response> response, String targetId) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).contains("/index.html"),
                () -> assertThat(DataBase.findUserById(targetId)).isNotNull()
        );
    }

    public static void retrievedUserList(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.header(HttpHeaders.CONTENT_TYPE)).contains("text/html;charset=utf-8")
        );
    }

    public static void redirectLoginPage(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).contains("/login.html")
        );
    }
}
