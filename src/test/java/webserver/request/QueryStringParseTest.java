package webserver.request;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryStringParseTest {
    @Test
    @DisplayName("Query String을 파싱하여 param의 정보를 key, value 형태로 얻을 수 있다.")
    void parseTest() {
        String queryString = "name1=value1&name2=value2";

        QueryString result = new QueryString(queryString);

        assertEquals(result.getParams(), Map.of("name1", "value1", "name2", "value2"));
    }
}
