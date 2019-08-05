package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametersTest {
    @ParameterizedTest
    @CsvSource({"field1=value1,1","field1=value1&field2=value2,2"})
    void 객체생성(ArgumentsAccessor argumentsAccessor) {
        //when
        Parameters parameters = Parameters.newInstance(argumentsAccessor.getString(0));

        //then
        assertThat(parameters.getParameters().size()).isEqualTo(argumentsAccessor.getInteger(1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"field1=&", "=value1", "field1==value1", "&"})
    void 잘못된_패턴검사(String queryString) {
        //when & then
        assertThrows(IllegalArgumentException.class,
                () -> Parameters.newInstance(queryString));
    }

    @Test
    void queryString이_없는경우() {
        //when
        Parameters parameters = Parameters.emptyInstance();

        //then
        assertThat(parameters.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"field1=value1,field1,value1","field1=value1&field2=value2,field2,value2"})
    void findByKey(ArgumentsAccessor argumentsAccessor) {
        //when
        Parameters parameters = Parameters.newInstance(argumentsAccessor.getString(0));
        String value = parameters.findByKey(argumentsAccessor.getString(1));

        //then
        assertThat(value).isEqualTo(argumentsAccessor.getString(2));
    }

    @ParameterizedTest
    @CsvSource({"field1=value1,field1,value1","field1=value1&field2=value2,field2,value2"})
    void addAll(ArgumentsAccessor argumentsAccessor) {
        //when
        Parameters parameters = Parameters.emptyInstance();
        parameters.addAll(argumentsAccessor.getString(0));

        //then
        assertThat(parameters.findByKey(argumentsAccessor.getString(1)))
                .isEqualTo(argumentsAccessor.getString(2));
    }
}
