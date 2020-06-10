package http.request;

import org.apache.logging.log4j.util.Strings;
import utils.UrlUtf8Decoder;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.WebServerException;

import java.util.HashMap;
import java.util.Map;

public class FormData {
    private static final String FORM_DATA_TOKENIZER = "&";
    private static final String FORM_DATA_NAME_VALUE_TOKENIZER = "=";
    private static final String FORM_DATA_DEFAULT_VALUE = Strings.EMPTY;

    private final Map<String, String> data = new HashMap<>();

    public FormData(String value) {

        for (String v : value.split(FORM_DATA_TOKENIZER)) {
            final String[] v1 = v.split(FORM_DATA_NAME_VALUE_TOKENIZER);
            if (v1.length != 2) {
                throw new WebServerException(ErrorMessage.ILLEGAL_FORM_DATA);
            }

            final String decodedName = UrlUtf8Decoder.decode(v1[0]).trim();
            final String decodedValue = UrlUtf8Decoder.decode(v1[1]).trim();

            if (decodedName.isEmpty()) {
                throw new WebServerException(ErrorMessage.ILLEGAL_FORM_DATA);
            }

            if (decodedValue.isEmpty()) {
                throw new WebServerException(ErrorMessage.ILLEGAL_FORM_DATA);
            }

            data.put(decodedName, decodedValue);
        }
    }

    public String getValue(String name) {
        return data.getOrDefault(name, FORM_DATA_DEFAULT_VALUE);
    }

}
