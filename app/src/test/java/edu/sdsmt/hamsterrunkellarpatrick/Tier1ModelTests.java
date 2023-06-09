package edu.sdsmt.hamsterrunkellarpatrick;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class Tier1ModelTests {

    //starting values
    static final int STORED_FOOD = 0;
    static final  int START_ENERGY = 10;
    static final int START_FOOD_IN_POUCHES = 0;
    static final  int START_MOVE_CNT = 0;
    static final  int START_ZOOMS_LEFT = 0;
    static final  int X = 0;
    static final  int Y = 0;


    //update values
    static final int MOVE_ENERGY = 1;
    static final int FOOD_PICK_AMOUNT = 5;
    static final int ZOOM_MOVE_ENERGY = 2;
    static final int ZOOM_START_MOVES = 2;
    static final int EAT_ENERGY = 5;
    static final int FOOD_PASS_THROUGH_AMOUNT = 2;

    //thresholds
    static final int FOOD_IN_AREA_2_2 = 5;
    static final int WIN_AMOUNT = 30;
    static final int MAX_ENERGY = 15;
    static final int MAX_FOOD_IN_POUCHES = 20;
    static final int BARS_LIMIT = 5;
    static final int HEAVY_HAMSTER = 15;


    @Test
    public void starting_values_correct() {
        Game g = new Game();

        assertEquals(STORED_FOOD, g.getHomeStores() );
        assertEquals(START_ENERGY, g.getEnergy() );
        assertEquals(START_FOOD_IN_POUCHES, g.getFood() );
        assertEquals(START_MOVE_CNT, g.getMoves());
        assertEquals(START_ZOOMS_LEFT, g.getZoomsLeft( ));
        assertEquals(X, g.getPlayerLocation().x);
        assertEquals(Y, g.getPlayerLocation().y);
    }


    @Test
    public void move_values_correct() {
        Game g = new Game();
        g.move(1, 0);
        assertEquals(g.getMoves(), 1);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(1, g.getPlayerLocation().x );
        assertEquals(0, g.getPlayerLocation().y);

        g.move(0, 1);
        assertEquals(g.getMoves(), 2);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(1, g.getPlayerLocation().x );
        assertEquals(1, g.getPlayerLocation().y);

        g.move(-1, 0);
        assertEquals(g.getMoves(), 3);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(0, g.getPlayerLocation().x);
        assertEquals(1, g.getPlayerLocation().y);

        g.move(0, -1);
        assertEquals(g.getMoves(), 4);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(0, g.getPlayerLocation().x);
        assertEquals(0, g.getPlayerLocation().y);

        g.move(-1, 0);
        assertEquals(g.getMoves(), 4);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(0, g.getPlayerLocation().x);
        assertEquals(0, g.getPlayerLocation().y);

        g.move(0, -1);
        assertEquals(g.getMoves(), 4);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(0, g.getPlayerLocation().x);
        assertEquals(0, g.getPlayerLocation().y);

        //should place in home locations
        g.move(4, 4);
        g.move(1, 0);
        assertEquals(5, g.getMoves());
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(4, g.getPlayerLocation().x);
        assertEquals(4,g.getPlayerLocation().y);

        g.move(0, 1);
        assertEquals(5, g.getMoves());
        assertEquals(START_ENERGY-g.getMoves() * MOVE_ENERGY,g.getEnergy() );
        assertEquals(4, g.getPlayerLocation().x);
        assertEquals(4,g.getPlayerLocation().y);
    }

    @Test
    public void eat_effect_correct() {
        Game g = new Game();

        //no food, no effect
        g.eat();
        assertEquals(0, g.getFood());
        assertEquals(START_ENERGY, g.getEnergy());

        //pickup food, and eat
        g.move(0, 1);
        g.pickup();
        g.eat();
        assertEquals(4, g.getFood());
        assertEquals(START_ENERGY+EAT_ENERGY-1, g.getEnergy());

        // check for max energy
        g.eat();
        g.eat();
        assertEquals(MAX_ENERGY, g.getEnergy());

        //eat while full tosses food
        g.eat();
        assertEquals(1, g.getFood());
        assertEquals(MAX_ENERGY, g.getEnergy());

    }

//    @Test
//    public void barrier_effect_correct() {
//        Game g = new Game();
//        g.move(1, 0);
//        g.move(1, 0);
//        assertEquals(1, g.getMoves());
//        assertEquals(START_ENERGY-1, g.getEnergy());
//        assertEquals(g.getHamsterLocation().x, 1);
//        assertEquals(g.getHamsterLocation().y, 0);
//    }

    @Test
    public void zoom_pickup_correct() {
        Game g = new Game();

        g.move(2, 1);
        g.pickup();
        assertEquals(g.getZoomsLeft(), 1);
    }

    @Test
    public void food_area_correct() {
        Game g = new Game();
        g.move(0, 1);
        g.pickup();
        assertEquals(FOOD_PICK_AMOUNT, g.getFood());

        //no more food to pick up check
        g.pickup();
        assertEquals(FOOD_PICK_AMOUNT, g.getFood());

        //go to new food pile, and collect it all
        g.move(0, 2);
        g.pickup();
        assertEquals(FOOD_PICK_AMOUNT * 2, g.getFood());
        g.pickup();
        assertEquals(FOOD_PICK_AMOUNT * 3, g.getFood());


        //move to on more pile, until cannot collect anymore food
        g.move(2, -1);
        g.pickup();
        assertEquals(MAX_FOOD_IN_POUCHES, g.getFood());
        g.pickup();
        assertEquals(MAX_FOOD_IN_POUCHES, g.getFood());

        //scatter food
        g.pickup();
        g.pickup();
        g.pickup();

        //test that there is no more, even if there is room in the pouches
        g.eat();
        g.pickup();
        assertEquals(MAX_FOOD_IN_POUCHES-1, g.getFood());

    }

    @Test
    public void home_deposit_correct() {
        Game g = new Game();

        //collect food
        g.move(0, 1);
        g.pickup();
        g.move(0, 1);
        g.move(0, 1);
        g.pickup();
        g.pickup();

        //deposited?
        g.move(4,1);
        assertEquals(FOOD_PICK_AMOUNT *3, g.getHomeStores());
        assertEquals(0, g.getFood());


        //collect food, again
        g.move(-2, -2);
        g.pickup();
        g.eat(); //to edit amount

        //deposited?
        g.move(2,2);
        assertEquals(FOOD_PICK_AMOUNT *4-1, g.getHomeStores());
        assertEquals(0, g.getFood());
    }

    @Test
    public void bar_effect_correct() {
        Game g = new Game();

        g.move(0, 1);
        g.pickup();

        g.move(0, 1);
        g.move(0, 1);
        g.pickup();

        //check food before and after
        assertEquals(FOOD_PICK_AMOUNT*2, g.getFood());
        g.move(3, 0);
        assertEquals(BARS_LIMIT, g.getFood());

    }

    @Test
    public void no_energy_loss_correct() {
        Game g = new Game();
        boolean right = true;
        for(int i = 0; i <= START_ENERGY ; i++) {
            if(right)
                g.move(1, 0);
            else
                g.move(-1, 0);
            right = !right;
        }

        assertTrue(g.isLost());
    }

    @Test
    public void caught_loss_correct() {
        Game g = new Game();
        g.move(2, 3);
        g.pickup();
        assertTrue(g.isLost());
    }

    @Test
    public void win_correct() {
        Game g = new Game();

        g.move(0, 1);
        g.pickup();
        g.move(0, 2);
        g.pickup();
        g.pickup();

        //deposited, current amount
        g.move(4, 1);
        assertEquals(FOOD_PICK_AMOUNT *3, g.getHomeStores() );

        //collect more and deposit
        g.move(-2,-2);
        g.pickup();
        g.pickup();
        g.pickup();
        g.move(2,2);
        assertEquals(WIN_AMOUNT, g.getHomeStores());

        assertTrue(g.isWon());


    }


}