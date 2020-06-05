package http;

import utils.UrlUtf8Decoder;

import java.util.HashMap;
import java.util.Map;

public class FormData {
    private static final String FORM_DATA_TOKENIZER = "&";
    private static final String FORM_DATA_NAME_VALUE_TOKENIZER = "=";

    private Map<String, String> data = new HashMap<>();

    public FormData(String encodedValue) {

        String value = UrlUtf8Decoder.decode(encodedValue);

        for (String v : value.split(FORM_DATA_TOKENIZER)) {
            String[] v1 = v.split(FORM_DATA_NAME_VALUE_TOKENIZER);
            if (v1.length != 2) {
                throw new RuntimeException();
            }
            data.put(v1[0], v1[1]);
        }
    }

    public String getValue(String name) {
        String defaultValue = "";
        return data.getOrDefault(name, defaultValue);
    }

}
