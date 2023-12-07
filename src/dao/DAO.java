package dao;

import javafx.collections.ObservableList;

public interface DAO<T> 
{
    ObservableList<T> getAll();
    T get(String id);
    void insert(T t);
    void update(T t);
    void delete(String id);
}
