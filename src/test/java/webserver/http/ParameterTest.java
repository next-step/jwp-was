package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterTest {
    @ParameterizedTest
    @CsvSource({"userId=testUser,userId,testUser"
            , "password=!!password,password,!!password"
            , "name=JaeSung,name,JaeSung"})
    void parameter생성(ArgumentsAccessor argumentsAccessor) {
        Parameter parameter = Parameter.newInstance(argumentsAccessor.getString(0));

        //then
        assertThat(parameter.getKey()).isEqualTo(argumentsAccessor.getString(1));
        assertThat(parameter.getValue()).isEqualTo(argumentsAccessor.getString(2));
    }
}