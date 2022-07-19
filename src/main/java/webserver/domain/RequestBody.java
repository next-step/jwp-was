package webserver.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestBody {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String value;

    public RequestBody(){}

    public RequestBody(String value) {
        this.value = value;
    }

    public <T> T convert(Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(value, type);
    }

    public String getValue() {
        return value;
    }
}
