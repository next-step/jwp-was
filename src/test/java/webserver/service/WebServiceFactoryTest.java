package webserver.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class WebServiceFactoryTest {
    @ParameterizedTest
    @CsvSource({"/user/create,true", "/user/list,false"})
    void create_userService(ArgumentsAccessor argumentsAccessor) {
        //when
        WebService webService = WebServiceFactory.create(argumentsAccessor.getString(0));

        //then
        assertThat(webService != null).isEqualTo(argumentsAccessor.getBoolean(1));
    }
}
