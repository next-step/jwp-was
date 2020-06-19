package testUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Request {

    public static ResponseEntity<String> get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }

}
