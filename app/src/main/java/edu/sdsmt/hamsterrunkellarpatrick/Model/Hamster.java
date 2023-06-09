package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Hamster {

    //energy can be in the range [0,15]
    private int energy = 10;

    //food can be in the range [0,20]
    private int food = 0;
    private boolean lost = false;
    private int moveEnergy = 1;
    private boolean won = false;
    private int zoomMovesLeft = 0;
    private int zoomPower = 0;

    /**
     * Description: Add food to hamster
     * */
    public void addFood() {

        //add 5 food, set max at 20
        food += 5;
        if (food > 20) {
            food = 20;
        }
    }

    /**
     * Description: Add a zoom power up
     * */
    public void addZoom() {
        zoomPower += 1;
    }

    /**
     * Description: Eat a food unit if available
     * */
    public void eat() {
        if (food > 0) {

            //remove food and add energy
            food -= 1;
            energy += 5;

            //max energy at 15
            if (energy > 15) {
                energy = 15;
            }

            lost = false;
        }
    }

    public int getEnergy() {
        return energy;
    }

    public int getFood() {
        return food;
    }

    public int getZoomMovesLeft() {
        return zoomMovesLeft;
    }

    public int getZoomsLeft() {
        return zoomPower;
    }

    public boolean isLost() {
        //Game is lost if energy goes negative or the hamster is caught
        return lost;
    }

    public boolean isWon() {
        return won;
    }

    /**
     * Description: If out of energy, set lost to true
     * */
    public void lostCheck() {
        if (energy < 0) {
            lost = true;
        }
    }

    public void removeMoveEnergy() {
        energy -= moveEnergy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setFood(int newFood) {
        food = newFood;
    }

    public void setLost(boolean lostState) {
        lost = lostState;
    }

    public void setMoveEnergy(int moveEnergy) {
        this.moveEnergy = moveEnergy;
    }

    public void setWon(boolean wonState) {
        won = wonState;
        lost = false;
    }

    public void setZoomMovesLeft(int zoomMovesLeft) {
        this.zoomMovesLeft = zoomMovesLeft;
    }

    public void setZooms(int zoomPower) {
        this.zoomPower = zoomPower;
    }

    public void useZoom() {
        zoomPower -= 1;
    }
}
