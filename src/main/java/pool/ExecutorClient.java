package pool;

public class ExecutorClient {
    private CustomThreadPool threadPoolA;
    private CustomThreadPool threadPoolB;

    private int poolSize;

    public ExecutorClient() {
        this(5);
    }

    public ExecutorClient(int poolSize) {
        this.poolSize = poolSize;

        threadPoolA = new CustomThreadPool(5, "A");
        threadPoolB = new CustomThreadPool(5, "B");
    }

    /**
     *
     * @param event
     */
    public void addEvent(Event event) {
        if (event.getType() == EventType.A) {
            threadPoolA.execute(new QueryWorker(event));
            System.out.println("A: " + threadPoolA.getWorkQueue());
        } else {
            threadPoolB.execute(new QueryWorker(event));
            System.out.println("B: " + threadPoolB.getWorkQueue());
        }
    }

    public void shutdown() {
        threadPoolA.shutdown();
        threadPoolB.shutdown();
    }
}