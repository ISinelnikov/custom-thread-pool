package pool;

import java.util.UUID;

public class Query {
    private int id;

    private String uuid;

    private QueryType type;

    public Query(int id, QueryType type) {
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

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Query {" +
                "id: " + id +
                ", uuid: " + uuid +
                ", type: " + type +
                '}';
    }
}
