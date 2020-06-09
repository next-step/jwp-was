package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

@DisplayName("웹 서버 테스트")
class WebServerTest {
    @Test
    void threadPool() throws Exception {
        // RequestHandler 에 thread.sleep 을 1초 정도 걸고 해보면 큐에서 reject 하는 걸 볼 수 있다.
        Thread thread = new Thread(() -> {
            try {
                WebServer.main(new String[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(2000);

        AtomicInteger idx = new AtomicInteger(0);
        for (int i = 0 ; i < 1000 ; ++i) {
            System.out.println(i + " 번째 요청을 추가합니다.");

            Thread req = new Thread(() -> {
                URL url = null;
                try {
                    url = new URL("http://localhost:8080/index.html?idx=" + idx.getAndAdd(1));
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    int responseCode = con.getResponseCode();
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println(idx.get() + " 응답 : " + responseCode);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            req.start();
        }
    }
}
