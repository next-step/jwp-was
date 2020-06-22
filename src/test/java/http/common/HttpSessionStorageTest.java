package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionStorageTest {

    @BeforeEach
    void setUp() {
        HttpSessionStorage.clear();
    }

    @DisplayName("등록되지 않은 세션 아이디일 경우 세션 객체 생성하여 저장 후 반환")
    @Test
    void test_getOrCreate_new() {
        // given
        // when
        HttpSession session = HttpSessionStorage.getOrCreate(null);
        // then
        assertThat(HttpSessionStorage.size()).isEqualTo(1);
    }

    @DisplayName("등록된 세션 아이디일 경우 세션 객체 반환")
    @Test
    void test_getOrCreate_already_exist() {
        // given
        HttpSession session = HttpSessionStorage.getOrCreate(null);
        // when
        HttpSession existing = HttpSessionStorage.getOrCreate(session.getId());
        // then
        assertThat(existing).isEqualTo(session);
    }
}