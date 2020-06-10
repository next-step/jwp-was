package webserver;

public class ThreadPoolServiceConfiguration {

    private final int initSizeOfThreadPool;
    private final int sizeOfThreadPool;
    private final int keepAliveTimeSecond;
    private final int workQueueCapacity;

    public ThreadPoolServiceConfiguration(int initSizeOfThreadPool, int sizeOfThreadPool, int keepAliveTimeSecond, int workQueueCapacity) {
        this.initSizeOfThreadPool = initSizeOfThreadPool;
        this.sizeOfThreadPool = sizeOfThreadPool;
        this.keepAliveTimeSecond = keepAliveTimeSecond;
        this.workQueueCapacity = workQueueCapacity;
    }

    public int getInitSizeOfThreadPool() {
        return initSizeOfThreadPool;
    }

    public int getSizeOfThreadPool() {
        return sizeOfThreadPool;
    }

    public int getKeepAliveTimeSecond() {
        return keepAliveTimeSecond;
    }

    public int getWorkQueueCapacity() {
        return workQueueCapacity;
    }
}
