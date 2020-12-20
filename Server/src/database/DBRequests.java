package database;

public interface DBRequests<T, Type> {
    T find(Type type);

    void insert(Type type);

    void delete(Type type);

    void update(Type type);

    T selectAll();

    void create();

    T select(T type);
}
