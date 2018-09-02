package pool;

enum QueryType {
    A, B;

    public static QueryType getQueryTypeToInt(int num) {
        return num == 0 ? A : B;
    }
}
