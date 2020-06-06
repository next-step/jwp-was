import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LanguageTest {

    @Test
    public void splitTest() {
        String cookieString = "userId=123";

        String[] values = cookieString.split("; ");

        assertThat(values.length).isEqualTo(1);
        assertThat(values[0]).isEqualTo(cookieString);
    }

    @Test
    public void multiValueMapToQueryStringTest() {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
        parameters.add("userId", "1234");
        parameters.add("searchType", "NAME");
        parameters.add("searchType", "EMAIL");

        String result = parameters.keySet().stream()
                .map(key -> key + "=" + String.join(",", parameters.get(key)))
                .collect(Collectors.joining("&"));

        assertThat(result).isEqualTo("userId=1234&searchType=NAME,EMAIL");
    }
}
