package ru.leonvsg.education.timesheet.dao.basic;

import java.sql.SQLException;
import java.util.List;

public interface DAO<ENTITY, KEY> {

    boolean create(ENTITY entity);

    ENTITY read(KEY key) throws EntityPersistanceException;

    boolean update(ENTITY entity);

    boolean delete(KEY key);

    List<ENTITY> getAll();
}
