package header;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public class StringSetter implements HeaderSetter<String> {
    @Override
    public String setEliment(String[] keyValue) {
        return keyValue[1];
    }
}
