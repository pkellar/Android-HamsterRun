package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Foodpile extends GridArea {

    private int units;

    public Foodpile(int foodUnits) {
        units = foodUnits;
    }

    /**
     * Description: Get how many food units are in the pile
     * */
    @Override
    public int getFoodUnits() {
        return units;
    }

    /**
     * Description: Pickup action for food pile
     *
     * @param hamster the hamster picking food up
     * */
    @Override
    public void pickup(Hamster hamster) {

        //if there is food, remove one and add it to the hamster
        if (units != 0) {
            units -= 1;
            hamster.addFood();
        }
    }

    /**
     * Description: Set home many food units are in this food pile
     *
     * @param units the amount of food in this pile
     * */
    @Override
    public void setFoodUnits(int units) {
        this.units = units;
    }
}
