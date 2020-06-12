package http.request;

import org.apache.logging.log4j.util.Strings;
import utils.UrlUtf8Decoder;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.IllegalParameterException;
import webserver.exceptions.WebServerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parameters {
    private static final String PARAMETER_TOKENIZER = "&";
    private static final String PARAMETER_NAME_VALUE_TOKENIZER = "=";
    private static final String PARAMETER_DEFAULT_VALUE = "";

    private final Map<String, String> data;

    public Parameters(String value) {
        data = new HashMap<>();

        if (Strings.isBlank(value)) {
            return;
        }

        for (String v : value.split(PARAMETER_TOKENIZER)) {
            final String[] v1 = v.split(PARAMETER_NAME_VALUE_TOKENIZER);

            validateParameterTokenSize(v1, value);

            final String decodedName = decodeWithTrim(v1[0]);
            final String decodedValue = decodeWithTrim(v1[1]);

            validateEmptyParameter(decodedName, value);
            validateEmptyParameter(decodedValue, value);

            data.put(decodedName, decodedValue);
        }
    }

    private void validateParameterTokenSize(String[] s, String value) {
        if (s.length != 2) {
            throw new IllegalParameterException(value);
        }
    }

    private String decodeWithTrim(String s) {
        return UrlUtf8Decoder.decode(s).trim();
    }

    public String getValue(String name) {
        return data.getOrDefault(name, PARAMETER_DEFAULT_VALUE);
    }

    private void validateEmptyParameter(String s, String value) {
        if (s.isEmpty()) {
            throw new IllegalParameterException(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
