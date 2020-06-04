package utils;

import model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldNameUtilsTest {

    @Test
    void fileLoadTest() {
        List<String> fieldNameMap = FieldNameUtils.classFieldNames(User.class);

        assertTrue(fieldNameMap.contains("userId"));
        assertTrue(fieldNameMap.contains("password"));
        assertTrue(fieldNameMap.contains("name"));
        assertTrue(fieldNameMap.contains("email"));
    }
}
