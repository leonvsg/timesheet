package ru.leonvsg.education.timesheet.services.verification;

public abstract class Handler {

    private Handler next;

    public Handler setNextHandler(Handler handler){
        this.next = handler;
        return next;
    }

    public abstract boolean handle();

    protected boolean checkNext() {
        return next == null || next.handle();
    }
}
