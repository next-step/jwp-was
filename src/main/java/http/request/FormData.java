package http.request;

import utils.UrlUtf8Decoder;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.WebServerException;

import java.util.HashMap;
import java.util.Map;

public class FormData {
    private static final String FORM_DATA_TOKENIZER = "&";
    private static final String FORM_DATA_NAME_VALUE_TOKENIZER = "=";

    private Map<String, String> data = new HashMap<>();

    public FormData(String value) {

        for (String v : value.split(FORM_DATA_TOKENIZER)) {
            String[] v1 = v.split(FORM_DATA_NAME_VALUE_TOKENIZER);
            if (v1.length != 2) {
                throw new WebServerException(ErrorMessage.ILLEGAL_FORM_DATA);
            }

            String decodedName = UrlUtf8Decoder.decode(v1[0]).trim();
            String decodedValue = UrlUtf8Decoder.decode(v1[1]).trim();

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
        String defaultValue = "";
        return data.getOrDefault(name, defaultValue);
    }

}
