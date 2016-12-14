package fr.m2sili.mtroysi.horserace;

/**
 * Created by Morgane TROYSI on 12/9/16.
 */

public class Horse {
    private int num;
    private int progression;
    private boolean inProgress;
    private int total_distance;
    private boolean isBoosted;

    public Horse() {
        this.progression = 0;
        this.inProgress = false;
        this.isBoosted = false;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getProgression() {
        return progression;
    }

    public void setProgression(int progression) {
        this.progression = progression;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public int getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(int total_distance) {
        this.total_distance = total_distance;
    }

    public boolean isBoosted() {
        return isBoosted;
    }

    public void setBoosted(boolean boosted) {
        isBoosted = boosted;
    }
}
