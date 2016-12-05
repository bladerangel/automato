package models;

public class State {

    private String name;
    private boolean marked;
    private boolean start;

    public State(String name) {
        this.name = name;
        this.marked = false;
        this.start = false;
    }


    public void start() {
        this.start = true;
    }

    public boolean isStart() {
        return start;
    }

    public void marked() {
        this.marked = true;
    }

    public boolean isMarked() {
        return marked;
    }

    public String getName() {
        return name;
    }

}
