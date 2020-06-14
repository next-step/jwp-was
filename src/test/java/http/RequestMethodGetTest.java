package http;

import http.request.RequestMethodGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RequestMethodGetTest {

    private RequestMethodGet requestMethodGet;

    @BeforeEach
    void setUp() {
        requestMethodGet = new RequestMethodGet("/users");
    }

    @Test
    @DisplayName("객체 생성")
    void createObject() {
        // give
        RequestMethodGet actualGet = new RequestMethodGet("/users");
        // when
        boolean same = actualGet.equals(new RequestMethodGet("/users"));
        //then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("메소드 이름 확인")
    void getMethodName() {
        // give
        String getMethodName = requestMethodGet.getMethodName();
        // when
        boolean same = getMethodName.equals("GET");
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("현재 패스 확인")
    void getPath() {
        // give
        String getPath = requestMethodGet.getPath();
        // when
        boolean same = getPath.equals("/users");
        // then
        assertThat(same).isTrue();
    }
}
