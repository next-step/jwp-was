package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WebApplicationServerTest {

	private static final String RANDOM_PORT = String.format("%04d", new Random().nextInt(10000));

	private final Thread WAS = new Thread(() -> {
		try {
			WebApplicationServer.main(new String[]{RANDOM_PORT, "5", "5"});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	});

	@Test
	@DisplayName("동시 10개 요청 테스트")
	public void parallelRequestTest()  {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://127.0.0.1:" + RANDOM_PORT + "/index.html";

		WAS.start();

		IntStream.range(0, 10)
				 .parallel()
				 .forEach(i -> {
					 ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
					 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
				 });

		WAS.interrupt();
	}
}
