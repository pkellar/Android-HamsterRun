package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Tile {
    private final GridArea gridArea;

    public Tile(GridArea newLandArea) {
        gridArea = newLandArea;
    }

    public int getFoodUnits() {
        return gridArea.getFoodUnits();
    }

    public int getHomeStores() {
        return gridArea.getHomeStores();
    }

    public Class<?> getTileClass() {
        return gridArea.getClass();
    }

    public void move(Hamster hamster) {
        gridArea.move(hamster);
    }

    public void pickup(Hamster hamster) {
        gridArea.pickup(hamster);
    }

    public void setFoodUnits(int units) {
        gridArea.setFoodUnits(units);
    }

    public void setHomeStores(int foodDeposited) {
        gridArea.setHomeStores(foodDeposited);
    }
}
