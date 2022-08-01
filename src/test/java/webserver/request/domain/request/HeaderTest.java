package webserver.request.domain.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.domain.request.Header;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeaderTest {

    @Test
    @DisplayName("header 비교 테스트")
    public void createRequestHeader () {
        List<String> list = new ArrayList<>();
        list.add("Host: localhost:8080");
        list.add("Connection: keep-alive");
        list.add("Accept: */*");
        Header header1 = new Header(list);

        Header header2 = new Header();
        header2.addHeaderProperty("Host: localhost:8080");
        header2.addHeaderProperty("Connection: keep-alive");
        header2.addHeaderProperty("Accept: */*");

        assertThat(header1).isEqualTo(header2);
    }

}
