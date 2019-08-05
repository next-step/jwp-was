package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParamsTest {

    @DisplayName("path QueryString parse: QueryString 없는 케이스")
    @ParameterizedTest
    @ValueSource(strings = {"/users"})
    public void parseByPathNoQuery(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isNull();
    }

    @DisplayName("path QueryString parse: QueryString value, value 구분자 없는 케이스")
    @ParameterizedTest
    @ValueSource(strings = {"/users?userId"})
    public void parseByPathQueryEmptyValue1(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isNull();
    }

    @DisplayName("path QueryString parse: QueryString value 없는 케이스")
    @ParameterizedTest
    @ValueSource(strings = {"/users?userId="})
    public void parseByPathQueryEmptyValue2(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isEmpty();
    }

    @DisplayName("path QueryString parse: QueryString 파라미터 조회")
    @ParameterizedTest
    @ValueSource(strings = {"/users?userId=javajigi&password=password&name=JaeSung"})
    public void parseByPath(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isEqualTo("javajigi");
    }

    @DisplayName("path QueryString parse: QueryString 동일 이름의 중복 파라미터 조회")
    @ParameterizedTest
    @ValueSource(strings = {"/users?userId=javajigi&userId=JaeSung"})
    public void parsebyPathSameName(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameterValues("userId")).containsExactly("javajigi", "JaeSung");
    }

    @DisplayName("path QueryString parse: QueryString url encoding 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"})
    public void parsebyPathUrlEncoded(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("name")).isEqualTo("박재성");
    }
}
