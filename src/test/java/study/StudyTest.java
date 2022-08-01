package study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StudyTest {

    private final String HEADER_KEY_VALUE_SEPARATOR = ": ";

    @DisplayName("String contain 판단 테스트")
    @Test
    void containTest() {
        String rightSampleHeader = "Connection: keep-alive";
        String wrongSampleHeader1 = "Connection:keep-alive";
        String wrongSampleHeader2 = "Connectionkeep-alive";
        String wrongSampleHeader3 = "Connection :keep-alive";
        Assertions.assertThat(rightSampleHeader.contains(HEADER_KEY_VALUE_SEPARATOR)).isTrue();
        Assertions.assertThat(wrongSampleHeader1.contains(HEADER_KEY_VALUE_SEPARATOR)).isFalse();
        Assertions.assertThat(wrongSampleHeader2.contains(HEADER_KEY_VALUE_SEPARATOR)).isFalse();
        Assertions.assertThat(wrongSampleHeader3.contains(HEADER_KEY_VALUE_SEPARATOR)).isFalse();
    }

    @DisplayName("toStringHttpMessageData 출력 테스트")
    @Test
    void stringBuilderTest() {
        List<String> httpMessageData = new ArrayList<>();
        httpMessageData.add("Host: localhost:8080");
        httpMessageData.add("Connection: keep-alive");
        httpMessageData.add("Accept: */*");

        System.out.println(this.toStringHttpMessageData(httpMessageData));
        Assertions.assertThat(1).isEqualTo(1);
    }

    private String toStringHttpMessageData(List<String> httpMessageData) {
        StringBuilder value = new StringBuilder();
        value.append("[" + "\n");
        httpMessageData.forEach(data -> value.append(data).append("\n"));
        value.append("]");

        return value.toString();
    }

}
