package ru.leonvsg.education.timesheet.services.verification;

public class Handler {

    private Handler next;
    private Checker checker;

    public Handler(Checker checker){
        this.checker = checker;
    }

    public boolean verify() {
        return checker.check() && checkNext();

    }

    public Handler setNextHandler(Handler handler){
        this.next = handler;
        return next;
    }

    protected boolean checkNext() {
        return next == null || next.verify();
    }
}
