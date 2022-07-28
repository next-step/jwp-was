package webserver.request.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exception.StringEmptyException;
import webserver.request.domain.request.RequestHeader;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestHeaderTest {

    @Test
    @DisplayName("header 비교 테스트")
    public void createRequestHeader () {
        List<String> list = new ArrayList<>();
        list.add("Host: localhost:8080");
        list.add("Connection: keep-alive");
        list.add("Accept: */*");
        RequestHeader requestHeader1 = new RequestHeader(list);

        RequestHeader requestHeader2 = new RequestHeader();
        requestHeader2.addHeaderProperty("Host: localhost:8080");
        requestHeader2.addHeaderProperty("Connection: keep-alive");
        requestHeader2.addHeaderProperty("Accept: */*");

        assertThat(requestHeader1).isEqualTo(requestHeader2);
    }

}
