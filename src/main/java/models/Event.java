package models;

public class Event {

    private String linkName;
    private State StateInit;
    private State stateFinal;

    public Event(String linkName, State stateInit, State stateFinal) {
        this.linkName = linkName;
        StateInit = stateInit;
        this.stateFinal = stateFinal;
    }

    public State getStateInit() {
        return StateInit;
    }

    public State getStateFinal() {
        return stateFinal;
    }


    public String getLinkName() {
        return linkName;
    }
}
