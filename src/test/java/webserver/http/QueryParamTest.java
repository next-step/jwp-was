package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;


class QueryParamTest {

    private static final String[] QUERY_STRING = {RequestFixture.PATH, RequestFixture.QUERY_STRING};

    @DisplayName("QueryString 정상 동작")
    @Test
    void queryStringParser() {

        QueryParam queryParam = new QueryParam(QUERY_STRING);

        then(queryParam.toString()).isNotEmpty();
    }

    @DisplayName("QueryString이 없는 경우")
    @Test
    void emptyQueryString() {

        final String[] splitStr = {RequestFixture.PATH};

        QueryParam queryParam = new QueryParam(splitStr);

        then(queryParam.toString()).isEmpty();
    }
}
