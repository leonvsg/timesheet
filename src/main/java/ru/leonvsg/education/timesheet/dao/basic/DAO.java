package ru.leonvsg.education.timesheet.dao.basic;

import java.util.List;

public interface DAO<E, K> {

    boolean create(E entity);

    E read(K key) throws EntityPersistanceException;

    boolean update(E entity);

    boolean delete(K key);

    List<E> getAll();
}
