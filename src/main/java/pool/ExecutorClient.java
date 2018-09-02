package pool;

public class ExecutorClient {
    private CustomThreadPool threadPoolA;
    private CustomThreadPool threadPoolB;

    ExecutorClient() {
        threadPoolA = new CustomThreadPool(5, "A");
        threadPoolB = new CustomThreadPool(5, "B");
    }

    public void addQuery(Query query) {
        if (query.getType() == QueryType.A) {
            threadPoolA.execute(new QueryWorker(query));
            System.out.println("A: " + threadPoolA.getWorkQueue());
        } else {
            threadPoolB.execute(new QueryWorker(query));
            System.out.println("B: " + threadPoolB.getWorkQueue());
        }
    }

    public void shutdown() {
        threadPoolA.shutdown();
        threadPoolB.shutdown();
    }
}