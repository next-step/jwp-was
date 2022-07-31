package request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpPathTest {

    @Test
    @DisplayName("쿼리 파라미터가 없는 instance를 만듭니다.")
    void notQueryStringTest() {
        String path = "/use/form.html";

        HttpPath httpPath = HttpPath.Instance(path);
        HttpPath 비교값 = new HttpPath(path, new QueryString());

        assertThat(httpPath).isEqualTo(비교값);
    }

    @Test
    @DisplayName("쿼리파라미터가 있는 instance를 만듭니다.")
    void isExistsQueryStringTest() {
        String path = "/user/create?userId=name&password=1234";

        HttpPath httpPath = HttpPath.Instance(path);
        HttpPath 비교값 = new HttpPath("/user/create", QueryString.parser("userId=name&password=1234"));

        assertThat(httpPath).isEqualTo(비교값);
    }

    @Test
    @DisplayName("파라미터 키 값을 주면 반환하는 테스트를 합니다.")
    void getParamterTest() {
        String path = "/user/create?userId=name&password=1234";

        HttpPath httpPath = HttpPath.Instance(path);

        assertThat(httpPath.getParameter("userId")).isEqualTo("name");
        assertThat(httpPath.getParameter("password")).isEqualTo("1234");
    }

}
