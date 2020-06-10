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
    private static final String PARAMETER_DEFAULT_VALUE = Strings.EMPTY;

    private final Map<String, String> data;

    public Parameters(String value) {
        data = new HashMap<>();

        if (Strings.isBlank(value)) {
            return;
        }

        for (String v : value.split(PARAMETER_TOKENIZER)) {
            final String[] v1 = v.split(PARAMETER_NAME_VALUE_TOKENIZER);
            if (v1.length != 2) {
                throw new IllegalParameterException(value);
            }

            final String decodedName = UrlUtf8Decoder.decode(v1[0]).trim();
            final String decodedValue = UrlUtf8Decoder.decode(v1[1]).trim();

            if (decodedName.isEmpty()) {
                throw new IllegalParameterException(value);
            }

            if (decodedValue.isEmpty()) {
                throw new IllegalParameterException(value);
            }

            data.put(decodedName, decodedValue);
        }
    }

    public String getValue(String name) {
        return data.getOrDefault(name, PARAMETER_DEFAULT_VALUE);
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
