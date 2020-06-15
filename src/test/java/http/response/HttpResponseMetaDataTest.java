package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseMetaDataTest {

    @DisplayName("Response Header Line 만들기")
    @Test
    void generateHeaderLine() {
        /* given */
        HttpResponseMetaData metaData = new HttpResponseMetaData();

        /* when */
        String headerLine = metaData.generateHeaderLine("Location", "/index.html");

        /* then */
        assertThat(headerLine).isEqualTo("Location: /index.html");
    }
}
