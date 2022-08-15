package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryParameterTest {

    @DisplayName("쿼리 파라미터를 파싱한다.")
    @Test
    void parseQueryParameter(){
        QueryParameter expectedParameter = new QueryParameter(Map.of(
                "userId", "jiwoni",
                "password", "1111"
        ));

        String[] splitUrl = {"/user", "userId=jiwoni&password=1111"};
        QueryParameter queryParameter = QueryParameter.parseFrom(splitUrl);

        assertEquals(expectedParameter, queryParameter);
    }
}
