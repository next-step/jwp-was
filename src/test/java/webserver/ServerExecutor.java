package webserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
*    웹서버를 실행하는 역할을 한다.
*    테스트 코드에서 서버를 띄워둘 필요가 있을 때 사용한다.
*    메인 스레드는 WAIT_MS 밀리초 대기한 후에 리턴한다.
*/
public class ServerExecutor {

    private final static long WAIT_MS = 3000;

    public static void execute(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            try {
                WebServer.main(args);
            } catch (Exception e) {
                es.shutdown();
            }
        });

        try {
            Thread.sleep(WAIT_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread.sleep() failed");
        }

        if (es.isShutdown()) {
            throw new RuntimeException("서버 실행 실패");
        }
    }
}