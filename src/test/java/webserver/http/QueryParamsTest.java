package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParamsTest {

    @ParameterizedTest
    @ValueSource(strings = {"/users"})
    public void parseByPathNoQuery(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?userId"})
    public void parseByPathQueryEmptyValue1(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?userId="})
    public void parseByPathQueryEmptyValue2(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?userId=javajigi&password=password&name=JaeSung"})
    public void parseByPath(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isEqualTo("javajigi");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?userId=javajigi&userId=JaeSung"})
    public void parsebyPathSameName(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameterValues("userId")).containsExactly("javajigi", "JaeSung");
    }
}
