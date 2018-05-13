package ru.leonvsg.education.timesheet.dao.basic;

public interface DAOFactory {

    <T extends DAO> T getDAO(Class clazz);
}
