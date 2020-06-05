package controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private final LoginController loginController = new LoginController();

    @Test
    void get() {
        assertThat(loginController.get(null));
    }
}