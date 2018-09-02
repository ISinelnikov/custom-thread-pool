package pool;

import java.util.Random;

public class QueryGenerator {
    private static final Random rnd = new Random(System.currentTimeMillis());

    private static Query getQuery() {
        QueryType type = QueryType.getQueryTypeToInt(rnd.nextInt(2));
        int x = rnd.nextInt(10);
        return new Query(x, type);
    }

    public QueryGenerator(int count, ExecutorClient client) {
        for (int i = 0; i < 20; i++) {
            Query query = QueryGenerator.getQuery();
            //Имитация времени генерации запроса
            try {
                Thread.sleep((i % 3) * 100);
            } catch (InterruptedException ignored) {
            }
            client.addQuery(query);
        }
    }
}
