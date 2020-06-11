package http;

import http.request.RequestParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParametersTest {

    private String queryString;

    @BeforeEach
    void setUp() {
        queryString = "userId=javajigi";
    }

    @Test
    @DisplayName("생성")
    void create() {
        // give
        RequestParameters requestParameters = new RequestParameters(queryString);
        // when
        boolean same = requestParameters.equals(new RequestParameters(queryString));
        // then
        assertThat(same).isTrue();
    }
}
