package org.ag.ktiles;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.Nullable;

public class Rhombus {
    private int x;
    private int y;

    private int value;

    public Rhombus(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = 0;
    }

    public void increment() {
        this.value++;
    }

    public int getX() {
        return x;
    }

    public int getValue() {
        return value;
    }

    public int getY() {
        return y;
    }

    @Nullable
    public ImmutablePair<Integer, Integer> getNeighbor(int dir) {
        return value % 2 == 0 ? this.getNeighborCoords(dir, true) : this.getNeighborCoords(dir, false);
    }

    /*
        Directions are, in order, down (10), right (11), up (12), left (13).
    */
    @Nullable
    private ImmutablePair<Integer, Integer> getNeighborCoords(int dir, boolean normal) {
        if (normal) {
            if (dir == 10) {                                      /*  Down */
                return new ImmutablePair<>(this.x, this.y + 1);
            } else if (dir == 11) {                                 /* Right */
                return new ImmutablePair<>(this.x + 1, this.y);
            } else if (dir == 12 && this.y > 0) {                   /*   Up  */
                return new ImmutablePair<>(this.x, this.y - 1);
            } else if (dir == 13 && this.x > 0) {                   /*  Left */
                return new ImmutablePair<>(this.x - 1, this.y);
            } else {
                return null;
            }
        } else {
            if (dir == 10) {                                          /*  Down */
                return new ImmutablePair<>(this.x + 1, this.y + 1);
            } else if (dir == 11 && this.y > 0) {                       /* Right */
                return new ImmutablePair<>(this.x + 1, this.y - 1);
            } else if (dir == 12 && this.y > 0 && this.x > 0) {         /*   Up  */
                return new ImmutablePair<>(this.x - 1, this.y - 1);
            } else if (dir == 13 && this.x > 0) {                       /*  Left */
                return new ImmutablePair<>(this.x - 1, this.y + 1);
            } else {
                return null;
            }
        }
    }
}
