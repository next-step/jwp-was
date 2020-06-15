package webserver.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseHeader {

    private String name;
    private List<String> values;

    public static ResponseHeader of(String name, String... values) {
        List<String> headerValues = Arrays.stream(values)
                .collect(toList());
        return new ResponseHeader(name, headerValues);
    }

    @Override
    public String toString() {
        return String.join(";", values) + "\r\n";
    }
}
