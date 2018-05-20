package ru.leonvsg.education.timesheet.services;

public interface ServiceFactory {

    <T extends Service> T getService(Class clazz);
}
