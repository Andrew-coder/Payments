package payments.dao;

import payments.model.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * interface which defines basic dao actions
 * @param <E>
 */
public interface CommonDao<E extends BaseEntity> {
    /**
     * this method search entity instance by specified id
     * @param id entity's id
     * @return Entity with specified id in Optional wrapper
     */
    Optional<E> findById(long id);

    /**
     * this methods finds all instancess in db
     * @return list with all instances
     */
    List<E> findAll();

    /**
     * create specified entity in db
     * @param e entity which will be created
     */
    void create(E e);

    /**
     * updates specified entity in db
     * @param e entity which will be updated
     */
    void update(E e);

    /**
     * delete instance in db with specified id
     * @param id entity's id
     */
    void delete(long id);
}