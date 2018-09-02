package pool;

import java.util.UUID;

public class Event {
    private int id;

    private String uuid;

    private EventType type;

    public Event(int id, EventType type) {
        this.id = id;
        this.type = type;
        uuid = UUID.randomUUID().toString();
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event {" +
                "id: " + id +
                ", uuid: " + uuid +
                ", type: " + type +
                '}';
    }
}
