package webserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerExecutor {

    public static void execute(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            try {
                WebServer.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread.sleep() failed");
        }
    }
}