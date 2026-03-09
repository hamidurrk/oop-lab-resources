package main;

public abstract class Star {
    protected String name;
    protected String mood;

    public Star(String name, String mood) {
        this.name = name;
        this.mood = mood;
    }

    // You need to decide which subclass does what with the wish
    public abstract void grantWish(Wish wish);

    public void introduce() {
        System.out.println("Star " + name + " is here, feeling " + mood + "!");
    }
}
