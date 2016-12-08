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

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isMarked() {
        return marked;
    }

    public String getName() {
        return name;
    }

}
