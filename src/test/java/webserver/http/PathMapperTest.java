package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import utils.HttpStringType;

import static org.assertj.core.api.Assertions.assertThat;

class PathMapperTest {
    @ParameterizedTest
    @CsvSource({"/,/index.html", "/user/create,/index.html", "/test,/test.html"})
    void filePath구하기(ArgumentsAccessor argumentsAccessor) {
        //when
        String path = PathMapper.getFilePath(argumentsAccessor.getString(0));

        //then
        assertThat(path).isEqualTo(HttpStringType.FILE_PATH_PREFIX.getType() + argumentsAccessor.getString(1));
    }
}