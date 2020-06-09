package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@DisplayName("스레드 풀 학습 테스트")
public class ThreadPoolStudy {

    @Test
    @DisplayName("블로킹 큐 스터디")
    void init() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(100);

        AtomicInteger atomic = new AtomicInteger(0);

        for (int i = 0 ; i < 200 ; ++i) {
            System.out.println(i + " 번째 요청을 추가합니다.");
            try {
                blockingQueue.add(() -> {
                    System.out.println(atomic.addAndGet(1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IllegalStateException e) {
                System.out.println("워커 큐가 가득차서 " + i + " 번째 요청은 버려집니다.");
            }
        }
    }

    @Test
    @DisplayName("스레드 풀 스터디")
    void threadPoolTest() throws InterruptedException {
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(100);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        10,
                        50,
                        1, TimeUnit.HOURS,
                        blockingQueue
                );

        AtomicInteger atomic = new AtomicInteger(0);

        for (int i = 0 ; i < 1000 ; ++i) {
            Thread.sleep(10);
            System.out.println(i + " 번째 요청을 추가합니다.");
            try {
                threadPoolExecutor.execute(() -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println(atomic.addAndGet(1) + " 번째 요청을 수행합니다.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (RejectedExecutionException e) {
                System.out.println("==================================워커 큐가 가득차서 " + i + " 번째 요청은 버려집니다.");
            }
        }
    }
}
