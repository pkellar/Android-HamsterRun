package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Person extends GridArea {

    /**
     * Description: The hamster gets snatched by a human, and the hamster looses
     *
     * @param hamster the hamster being picked up
     * */
    @Override
    public void pickup(Hamster hamster) {
        hamster.setLost(true);
    }

}
