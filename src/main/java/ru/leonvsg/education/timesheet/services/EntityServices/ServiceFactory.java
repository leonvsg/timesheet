package ru.leonvsg.education.timesheet.services.EntityServices;

public interface ServiceFactory {

    <T extends Service> T getService(Class clazz);
}
