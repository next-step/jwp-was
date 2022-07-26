package webserver.http.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParametersTest {

    @DisplayName("Parameters를 인자로 받아 내부의 저장한 파라미터정보를 모두 저장한다.")
    @Test
    void add() {
        HashMap<String, List<String>> params = new HashMap<>();
        params.put("key1", Lists.list("value1"));
        Parameters parameters = new Parameters(params);

        HashMap<String, List<String>> targetParams = new HashMap<>();
        targetParams.put("key2", Lists.list("value2"));
        targetParams.put("key3", Lists.list("value3"));
        Parameters target = new Parameters(targetParams);

        parameters.add(target);

        assertThat(parameters).usingRecursiveComparison()
                .isEqualTo(expected());
    }

    private Parameters expected() {
        HashMap<String, List<String>> params = new HashMap<>();
        params.put("key1", Lists.list("value1"));
        params.put("key2", Lists.list("value2"));
        params.put("key3", Lists.list("value3"));

        return new Parameters(params);
    }
}