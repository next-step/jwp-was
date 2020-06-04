package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RequestMethodGetTest {

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
}
