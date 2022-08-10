package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String convertObjectToJsonString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
