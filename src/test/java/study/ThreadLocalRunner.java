package study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ThreadLocalRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ThreadLocalRunner.class);

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void run() {
        String s = threadLocal.get();
        if (s == null) {
            threadLocal.set(UUID.randomUUID().toString());
            s = threadLocal.get();
        }
        final String n = UUID.randomUUID().toString();
        threadLocal.set(n);
        log.info("before: {}, after: {}", s, n);
    }
}
