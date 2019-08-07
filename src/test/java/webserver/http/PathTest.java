package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @ParameterizedTest
    @CsvSource({"/user,/user,true"
            , "/users?userId=javajigi&password=password&name=JaeSung,/users,false"
            , "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1,/create,false"
            , "/user/create?userId=a&password=b&name=c&email=d%40d.com,/user/create,false"})
    void path저장확인(ArgumentsAccessor argumentsAccessor) {
        //when
        Path path = Path.newInstance(argumentsAccessor.getString(0));

        //then
        assertThat(path.getPath()).isEqualTo(argumentsAccessor.getString(1));
        assertThat(path.getParameters().isEmpty()).isEqualTo(argumentsAccessor.getBoolean(2));
    }

    @ParameterizedTest
    @CsvSource({"/user/login, /success, /user/login/success"
            , "/user/login, /fail, /user/login/fail"})
    void addSubPath(ArgumentsAccessor argumentsAccessor) {
        //when
        Path path = Path.newInstance(argumentsAccessor.getString(0));
        path.addSubPath(argumentsAccessor.getString(1));

        //then
        assertThat(path.getPath()).isEqualTo(argumentsAccessor.getString(2));
    }
}
