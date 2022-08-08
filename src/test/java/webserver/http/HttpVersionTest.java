package webserver.http;

import exception.ResourceNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpVersionTest {

    @DisplayName("존재 하는 버전은 성공적으로 리턴한다.")
    @Test
    void existsVersion(){
        String validVersion = "1.1";

        HttpVersion version = HttpVersion.from(validVersion);

        assertEquals(version, HttpVersion.VER_1_1);
    }

    @DisplayName("존재 하지 않는 버전은 예외를 리턴한다.")
    @Test
    void notExistsVersion(){
        String inValidVersion = "1.5";

        assertThatExceptionOfType(ResourceNotFound.class)
                .isThrownBy(() -> HttpVersion.from(inValidVersion));
    }
}
