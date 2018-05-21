package ru.leonvsg.education.timesheet.services.verification;

public class AdaptiveHandler extends Handler {

    private Checker checker;

    public AdaptiveHandler(Checker checker) {
        this.checker = checker;
    }

    public boolean handle() {
        return checker.check() && checkNext();
    }
}
