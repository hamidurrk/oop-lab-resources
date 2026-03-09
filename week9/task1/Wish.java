package main;

public interface Wish {
    String getWisher();
    String getDesire();

    default String fullWish() {
        return getWisher() + " wishes for " + getDesire();
    }
}