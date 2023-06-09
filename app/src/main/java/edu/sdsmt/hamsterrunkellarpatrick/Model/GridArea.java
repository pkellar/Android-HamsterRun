package edu.sdsmt.hamsterrunkellarpatrick.Model;

public abstract class GridArea {

    /**
     * Description: Food units left for the hamster to get
     *
     * @return count of food units left on this tile
     * */
    public int getFoodUnits() {
        return 0;
    }

    /**
     * Description: Get food stored this home tile
     *
     * @return zero since this isn't a home tile
     * */
    public int getHomeStores() {
        return 0;
    }

    /**
     * Description: move action for the tile
     *
     * @param hamster the hamster being moved
     * */
    public void move(Hamster hamster) {
    }

    /**
     * Description: pickup action for the tile
     *
     * @param hamster the hamster picking up stuff
     * */
    public void pickup(Hamster hamster) {
    }

    /**
     * Description: Set the food units stored in the tile
     *
     * @param units the amount of food stored in the food pile
     * */
    public void setFoodUnits(int units) {
    }

    /**
     * Description: Set how much food is stored at home
     *
     * @param foodDeposited the count of food stored at home
     * */
    public void setHomeStores(int foodDeposited) {
    }
}
