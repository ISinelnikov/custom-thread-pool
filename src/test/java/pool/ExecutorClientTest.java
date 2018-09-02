package pool;

import org.junit.Test;

public class ExecutorClientTest {
    @Test
    public void test() {
        ExecutorClient executorClient = new ExecutorClient();
        new EventGenerator(10, executorClient, 10);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        executorClient.shutdown();
    }
}
