package configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadConfiguration {

    public static final Executor serviceThreadPool = new ThreadPoolExecutor(
            250,
            250,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100)
    );

//    public static final Executor serviceThreadPool = new ThreadPoolExecutor(
//            5,
//            5,
//            0,
//            TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<>(2)
//    );

}
