package models;

public class State {

    private String name;
    private boolean marked;
    private boolean start;
    private boolean accessible;

    public State(String name) {
        this.name = name;
        this.marked = false;
        this.start = false;
        this.accessible = false;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public String getName() {
        return name;
    }

}
