package payments.dao;

import payments.model.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface CommonDao<E extends BaseEntity> {
    Optional<E> findById(long id);
    List<E> findAll();
    void create(E e);
    void update(E e);
    void delete(long id);
}