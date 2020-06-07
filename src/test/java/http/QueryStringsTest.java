package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("QueryStrings 테스트")
public class QueryStringsTest {

    @DisplayName("해당 key를 가진 queryString이 존재 하지 않으면 null을 반환한다.")
    @Test
    void noQueryString() {
        QueryStrings queryStrings = new QueryStrings("userId=serverwizard&password=password&name=JongWan");
        assertThat(queryStrings.getValue("userId2")).isNull();
    }

    @DisplayName("잘못된 QueryString 포맷( key@value )을 가진 문자열을 전달하면, IllegalArgumentException 예외를 반환한다.")
    @Test
    void exception() {
        assertThatThrownBy(() -> new QueryStrings("userId@serverwizard"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 QueryString 포맷을 가진 문자열 입니다.");
    }

    @DisplayName("문자열로된 queryString을 가지고 QueryStrings 객체를 생성한다.")
    @Test
    void create() {
        QueryStrings queryStrings = new QueryStrings("userId=serverwizard&password=password&name=JongWan");
        assertThat(queryStrings).isEqualTo(new QueryStrings(
                        Arrays.asList(new QueryString("userId", "serverwizard"), new QueryString("password", "password"), new QueryString("name", "JongWan"))
                )
        );
    }
}
