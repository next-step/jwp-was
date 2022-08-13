package user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HandlerMapping;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerMappingTest {

    @DisplayName("ControllerEnum에 선언해둔 enum을 기준으로 ControllerMap이 생성됨을 확인한다.")
    @Test
    void construct() {
        HandlerMapping handlerMapping = new HandlerMapping();
        assertThat(handlerMapping.getControllerMap()).hasSize(3);
    }
}