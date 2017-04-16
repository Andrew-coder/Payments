package payments.dao;

import payments.model.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface CommonDao<E extends BaseEntity> {
    Optional<E> findById(int id);
    List<E> findAll();
    void create(E e);
    void update(E e);
    void delete(int id);
}