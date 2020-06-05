package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class FormData {
    private static final String FORM_DATA_TOKENIZER = "&";
    private static final String FORM_DATA_NAME_VALUE_TOKENIZER = "=";

    private Map<String, String> data = new HashMap<>();

    public FormData(String encodedValue) {

        String value = decode(encodedValue);

        for (String v : value.split(FORM_DATA_TOKENIZER)) {
            String[] v1 = v.split(FORM_DATA_NAME_VALUE_TOKENIZER);
            if (v1.length != 2) {
                throw new RuntimeException();
            }
            data.put(v1[0], v1[1]);
        }
    }

    private String decode(String encodedValue) {
        final String EncodeType = "UTF-8";
        String result = null;
        try {
            result = URLDecoder.decode(encodedValue, EncodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("지원하지 않는 Encoding 타입. Type : [" + EncodeType + "]");
        }
        return result;
    }

    public String getValue(String name) {
        String defaultValue = "";
        return data.getOrDefault(name, defaultValue);
    }

}
