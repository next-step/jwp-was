package webserver.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolRequestExecutor {
    private final Logger log = LoggerFactory.getLogger(ThreadPoolRequestExecutor.class);

    private ExecutorService executor;

    public ThreadPoolRequestExecutor(int nThreads) {
        log.debug("TheadPoolRequestExecutor initialize!!");
        this.executor = Executors.newFixedThreadPool(nThreads);
    }

    public void execute(RequestHandler requestHandler) {
        executor.execute(requestHandler);
    }
}
