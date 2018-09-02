package pool;

import org.junit.Test;

public class ExecutorClientTest {
    @Test
    public void test() {
        ExecutorClient executorClient = new ExecutorClient();
        new QueryGenerator(10, executorClient);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        executorClient.shutdown();
    }
}
