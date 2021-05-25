package ru.javawebinar.topjava.util;

public class Excess {
    private boolean isExcess;

    public Excess(boolean isExcess) {
        this.isExcess = isExcess;
    }

    public boolean isExcess() {
        return isExcess;
    }

    public void setExcess(boolean excess) {
        isExcess = excess;
    }

    @Override
    public String toString() {
        return "" + isExcess;
    }
}
