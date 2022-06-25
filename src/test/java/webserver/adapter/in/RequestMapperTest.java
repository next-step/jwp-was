package webserver.adapter.in;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.adapter.in.controller.Controller;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {

    @DisplayName("request path에 맞는 Controller를 매핑할 수 있다")
    @Test
    void mapping() {
        RequestMapper mapper = new RequestMapper(null);

        Controller actual = mapper.getController("/user/login");

        assertThat(actual.getClass().getSimpleName()).isEqualTo("LoginController");
    }
}
