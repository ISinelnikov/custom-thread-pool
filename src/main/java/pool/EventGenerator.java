package pool;

import java.util.Random;

public class EventGenerator {
    private final Random rnd = new Random(System.currentTimeMillis());

    private int maxId;

    private Event getEvent() {
        EventType type = EventType.getEventTypeByNum(rnd.nextInt(2));
        int id = rnd.nextInt(maxId);
        return new Event(id, type);
    }

    /**
     * Добавляет новые события в ExecutorClient
     *
     * @param eventCount - количество событий
     * @param client     - принимающая сторона
     * @param maxId      - максимальный id события
     */
    public EventGenerator(int eventCount, ExecutorClient client, int maxId) {
        this.maxId = maxId + 1;

        for (int count = 0; count < eventCount; count++) {
            Event event = getEvent();
            //Имитация времени генерации запроса
            try {
                Thread.sleep(rnd.nextInt(10) * 10);
            } catch (InterruptedException ignored) {
            }
            client.addEvent(event);
        }
    }
}
