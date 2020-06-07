package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ConvertUtils {
    public static <T> T convertValue(Map map, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, valueType);
    }
}
