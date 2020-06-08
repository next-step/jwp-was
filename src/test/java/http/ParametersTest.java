package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParametersTest {

    @Test
    @DisplayName("QueryString -> MultiValueMap 파싱 테스트")
    public void queryStringToMultiValueMapTest() {
        String queryString = "userId=1&name=KingCjy&status=ACTIVE,INACTIVE&age=";

        Parameters parameters = Parameters.from(queryString);

        assertThat(parameters.getParameter("userId")).isEqualTo("1");
        assertThat(parameters.getParameter("name")).isEqualTo("KingCjy");
        assertThat(parameters.getParameterValues("status")).isEqualTo(new String[] {"ACTIVE", "INACTIVE"});
        assertThat(parameters.getParameter("age")).isEqualTo("");
    }

    @Test
    @DisplayName("빈 QueryString 파싱 테스트")
    public void initFromEmptyQueryStringTest() {
        Parameters parameters = Parameters.from("");
    }
}
