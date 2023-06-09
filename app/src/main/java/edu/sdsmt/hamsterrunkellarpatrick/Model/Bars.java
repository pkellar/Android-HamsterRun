package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Bars extends GridArea {

    /**
     * Description: Moving the hamster through the bars which can remove food
     *
     * @param hamster the hamster being moved
     * */
    @Override
    public void move(Hamster hamster) {
        //Get the food the hamster currently has
        int food = hamster.getFood();

        //If the hamster's food count is more than 5, reduce it to 5
        if (food > 5) {
            hamster.setFood(5);
        }
    }
}
