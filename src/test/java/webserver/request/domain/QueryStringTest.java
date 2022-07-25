package webserver.request.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.domain.request.QueryString;

public class QueryStringTest {

    @Test
    @DisplayName("queryString을 pair로 나눌때 map 안에 있는 갯수를 확인하는 테스트")
    public void queryStringPairsTest() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        Assertions.assertThat(queryString.getQueryStringPairs()).isEqualTo(4);
    }
}
