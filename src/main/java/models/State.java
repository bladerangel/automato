package models;

public class State {

    private String name;

    public State(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        return name;
    }

    public String getName() {
        return name;
    }
}
