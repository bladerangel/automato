package models;

public class State {

    private String name;
    private boolean marked;

    public State(String name) {
        this.name = name;
        this.marked = false;
    }

    @Override
    public String toString() {

        return name;
    }

   public void marked(){
        this.marked = true;
   }

    public boolean isMarked() {
        return marked;
    }

    public String getName() {
        return name;
    }
}
