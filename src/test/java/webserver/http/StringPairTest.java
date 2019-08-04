package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringPairTest {

    @Test
    void split() {
        StringPair pair = StringPair.split("test:localhost:8080", ":", 1);

        assertThat(pair.getKey()).isEqualTo("test");
        assertThat(pair.getValue()).isEqualTo("localhost:8080");
    }
}