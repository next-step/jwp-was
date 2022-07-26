package study;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.assertj.core.api.Assertions.assertThat;

public class UriDecoderTest {

    @Test
    void name() throws UnsupportedEncodingException {
        final String decode = URLDecoder.decode("%EB%B0%95%EC%9E%AC%EC%84%B1", "UTF-8");

        assertThat(decode).isEqualTo("박재성");
    }
}
