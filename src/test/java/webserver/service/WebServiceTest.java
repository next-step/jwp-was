package webserver.service;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;


public class WebServiceTest {
    @Test
    void create_UserService() {
        //when
        WebService webService = new UserService();

        //then
        assertThat(webService.getClass()).isEqualTo(UserService.class);
    }
}
