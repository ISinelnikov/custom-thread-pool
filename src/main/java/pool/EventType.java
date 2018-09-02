package pool;

/**
 * Типы событий
 */
enum EventType {
    A, B;

    public static EventType getEventTypeByNum(int num) {
        return num == 0 ? A : B;
    }
}
