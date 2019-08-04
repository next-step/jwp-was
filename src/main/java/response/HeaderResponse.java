package response;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public interface HeaderResponse {
    String key();

    String value();

    default String keyValue() {
      return key() + value();
    }
}
