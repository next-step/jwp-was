package response;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public interface HeaderResponse {
    String KEY_VALUE_SPLITER = ": ";

    String key();

    String valueAll();

    default String keyValue() {
      return key() + KEY_VALUE_SPLITER + valueAll() + System.lineSeparator();
    }
}
