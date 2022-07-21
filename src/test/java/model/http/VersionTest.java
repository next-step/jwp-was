package model.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

    @DisplayName("version 객체를 생성합니다.")
    @Test
    void construct() {
        Version version = new Version("1.1");
        assertThat(version).isNotNull();
    }
}
