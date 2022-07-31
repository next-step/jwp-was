package model;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpPath;
import request.QueryString;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpPathTest {

    @Test
    @DisplayName("path에 queryString이 없는 경우")
    void isEmptyQueryString() {
        String path = "/nextstep";
        HttpPath 비교값 = new HttpPath("/nextstep", new QueryString());

        HttpPath httpPath = HttpPath.Instance(path);

        assertThat(httpPath).isEqualTo(비교값);
    }

    @Test
    @DisplayName("path에 queryString이 있는 경우")
    void isExistQueryString() {
        String path = "/nextstep?name=김배민&age=3";

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", "김배민");
        parameter.put("age", "3");
        HttpPath 비교값 = new HttpPath("/nextstep", new QueryString(parameter));

        HttpPath httpPath = HttpPath.Instance(path);

        assertThat(httpPath).isEqualTo(비교값);
    }

}
