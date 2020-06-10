package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("User 비밀번호 일치 확인하기")
    @Test
    void isSamePassword() {
        /* given */
        User user = User.builder()
                .userId("testId")
                .password("p@ssw0rd")
                .name("luke")
                .email("luke@naver.com")
                .build();

        /* when */
        boolean expected1 = user.isSamePassword("p@ssw0rd");
        boolean expected2 = user.isSamePassword("password");

        /* then */
        assertThat(expected1).isTrue();
        assertThat(expected2).isFalse();
    }
}