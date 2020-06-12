package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ConvertUtils {


    public static <T> T convertMapToObject(Map<String, String> params, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        T object = mapper.convertValue(params, toValueType);
        return object;
    }
}
