package http.request;

import http.common.HeaderField;
import http.common.HeaderFieldName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @Test
    void createReqeustHeader() {
        final String name = "Content-Type";
        final String value = "application/json";
        final HeaderField headerField = new HeaderField(name, value);
        final Map<String, HeaderField> input = Collections.singletonMap(name, headerField);

        new RequestHeader(input);
    }

    @Test
    void find() {
        final String name = "Content-Type";
        final String value = "application/json";
        final HeaderField headerField = new HeaderField(name, value);
        final Map<String, HeaderField> input = Collections.singletonMap(name, headerField);
        final RequestHeader requestHeader = new RequestHeader(input);

        final String result = requestHeader.getValue(name);

        assertThat(result).isEqualTo(value);
    }

    @Test
    void findByEnum() {
        final String name = "Content-Type";
        final String value = "application/json";
        final HeaderField headerField = new HeaderField(name, value);
        final Map<String, HeaderField> input = Collections.singletonMap(name, headerField);
        final RequestHeader requestHeader = new RequestHeader(input);
        final HeaderFieldName nameType = HeaderFieldName.CONTENT_TYPE;

        final String result = requestHeader.getValue(nameType);

        assertThat(result).isEqualTo(value);
    }

}