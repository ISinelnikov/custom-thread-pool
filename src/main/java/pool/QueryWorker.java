package pool;

/**
 * Выполняет работу над объектом типа query
 */
public class QueryWorker implements Runnable {
    private Query query;
    private int time;

    public Query getQuery() {
        return query;
    }

    /**
     * Создает новую работу
     * time - имитирует время работы над запросом
     *
     * @param query
     */
    public QueryWorker(Query query) {
        this.query = query;
        time = this.hashCode() % 3;
    }

    @Override
    public void run() {
        System.out.println("Выполняется работа для запроса типа " + query.getType() + " с весом " + query.getId() + " (" + query.getUuid() + ")");
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public String toString() {
        return query.toString() + ", time: " + time;
    }
}
