package ru.education.timesheet.DAO.Basic;

import java.sql.SQLException;
import java.util.List;

public interface DAO<ENTITY, KEY> {

    void create(ENTITY entity);

    ENTITY read(KEY key) throws SQLException;

    void update(ENTITY entity);

    void delete(KEY key);

    List<ENTITY> getAll();
}
