package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryParamsTest {

    @ParameterizedTest(name = "path QueryString parse: QueryString 없는 케이스 {index} : {0}")
    @ValueSource(strings = {"/users", "/users?userId"})
    public void parseByPathNoQuery(String path){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter("userId")).isNull();
    }

    @DisplayName("path QueryString parse: QueryString value 없는 케이스")
    @Test
    public void parseByPathQueryEmptyValue2(){
        QueryParams queryParams = QueryParams.parseByPath("/users?userId=");
        assertThat(queryParams.getParameter("userId")).isEmpty();
    }

    @DisplayName("path QueryString parse: QueryString 파라미터 조회")
    @ParameterizedTest
    @CsvSource(value = {
            "/users?userId=javajigi&password=password&name=JaeSung,userId,javajigi"
            ,"/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net,name,박재성"
    })
    public void parseByPath(String path, String parameterName, String expectedValue){
        QueryParams queryParams = QueryParams.parseByPath(path);
        assertThat(queryParams.getParameter(parameterName)).isEqualTo(expectedValue);
    }

    @DisplayName("path QueryString parse: QueryString 동일 이름의 중복 파라미터 조회")
    @Test
    public void parsebyPathSameName(){
        QueryParams queryParams = QueryParams.parseByPath("/users?userId=javajigi&userId=JaeSung");
        assertThat(queryParams.getParameterValues("userId")).containsExactly("javajigi", "JaeSung");
    }


}
