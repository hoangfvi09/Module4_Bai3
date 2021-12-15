package service;

import java.util.List;

public interface IService<T> {
    void save(T t);
    List<T> findAll();
    T findById(int id);
    void update(int id, T t);
    void delete(int id);
}
