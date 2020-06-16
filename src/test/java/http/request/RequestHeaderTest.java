package http.request;

import http.common.HeaderField;
import http.common.HeaderFieldName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @Test
    @DisplayName("RequestHeader 객체를 생성할 수 있다")
    void createReqeustHeader() {
        final String name = "Content-Type";
        final String value = "application/json";
        final HeaderField headerField = new HeaderField(name, value);
        final Map<String, HeaderField> input = Collections.singletonMap(name, headerField);

        new RequestHeader(input);
    }

    @Test
    @DisplayName("RequestHeader에서 이름으로 값을 가져올 수 있다")
    void find() {
        final String name = "Content-Type";
        final String value = "application/json";
        final HeaderField headerField = new HeaderField(name, value);
        final Map<String, HeaderField> input = Collections.singletonMap(name, headerField);
        final RequestHeader requestHeader = new RequestHeader(input);

        final Optional<String> result = requestHeader.getValue(name);

        assertThat(result).hasValue(value);
    }

    @Test
    @DisplayName("RequestHeader에서 이름타입으로 값을 가져올 수 있다")
    void findByEnum() {
        final String name = "Content-Type";
        final String value = "application/json";
        final HeaderField headerField = new HeaderField(name, value);
        final Map<String, HeaderField> input = Collections.singletonMap(name, headerField);
        final RequestHeader requestHeader = new RequestHeader(input);
        final HeaderFieldName nameType = HeaderFieldName.CONTENT_TYPE;

        final Optional<String> result = requestHeader.getValue(nameType);

        assertThat(result).hasValue(value);
    }

}