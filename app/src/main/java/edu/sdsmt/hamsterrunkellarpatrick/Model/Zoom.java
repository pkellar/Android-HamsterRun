package edu.sdsmt.hamsterrunkellarpatrick.Model;

public class Zoom extends GridArea {

    /**
     * Description: Pickup a zoom power up
     *
     * @param hamster the hamster getting an awesome new zoom power up
     * */
    @Override
    public void pickup(Hamster hamster) {
        hamster.addZoom();
    }
}
