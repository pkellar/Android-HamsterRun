package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Home extends GridArea {

    private int foodDeposited = 0;

    /**
     * Description: Get the amount of food stored at home
     *
     * @return the amount of food stored at home
     * */
    @Override
    public int getHomeStores() {
        return foodDeposited;
    }

    /**
     * Description: Remove the food the hamster has and check if it won
     *
     * @param hamster the hamster being moved
     * */
    @Override
    public void move(Hamster hamster) {
        foodDeposited += hamster.getFood();
        hamster.setFood(0);

        if (foodDeposited >= 15) {
            hamster.setWon(true);
        }
    }

    /**
     * Description: Set the food stored at home
     *
     * @param foodDeposited the food stored at home
     * */
    @Override
    public void setHomeStores(int foodDeposited) {
        this.foodDeposited = foodDeposited;
    }
}
