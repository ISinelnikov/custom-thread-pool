package pool;

/**
 * Выполняет работу над объектом типа event
 */
public class QueryWorker implements Runnable {
    private Event event;
    private int time;

    public Event getEvent() {
        return event;
    }

    /**
     * Создает новую работу
     * time - имитирует время работы над запросом
     *
     * @param event
     */
    public QueryWorker(Event event) {
        this.event = event;
        time = this.hashCode() % 3;
    }

    @Override
    public void run() {
        System.out.println("Выполняется работа для запроса типа " + event.getType() + " с весом " + event.getId() + " (" + event.getUuid() + ")");
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Завершена работа для запроса типа " + event.getType() + " с весом " + event.getId() + " (" + event.getUuid() + ")");
    }

    @Override
    public String toString() {
        return event.toString() + ", time: " + time;
    }
}
