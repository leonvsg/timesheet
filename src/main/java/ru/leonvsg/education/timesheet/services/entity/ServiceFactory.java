package ru.leonvsg.education.timesheet.services.entity;

public interface ServiceFactory {

    <T extends Service> T getService(Class clazz);
}
