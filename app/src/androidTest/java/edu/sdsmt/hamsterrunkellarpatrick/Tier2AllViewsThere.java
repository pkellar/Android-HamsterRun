package edu.sdsmt.hamsterrunkellarpatrick;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.widget.Button;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Tier2AllViewsThere {
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
    static final int WIN_AMOUNT = 15;
    static final int MAX_ENERGY = 15;
    static final int MAX_FOOD_IN_POUCHES = 20;
    static final int BARS_LIMIT = 5;
    static final int HEAVY_HAMSTER = 15;
    @Rule
    public ActivityScenarioRule<MainActivity> act = new ActivityScenarioRule<>(MainActivity.class);

    private Game g;
    private StateMachine sm;
    private Button zoomBtn;
    private Button eatBtn;
    private Button resetBtn;
    private Button upBtn;
    private Button downBtn;
    private Button leftBtn;
    private Button rightBtn;
    private TextView zoomView;
    private TextView moveView;
    private TextView energyView;
    private TextView storesView;
    private TextView foodView;

    //Initialize to have access to underlying game, state machine, and distract button
    private void init(){
        AtomicReference<Game> gameAtom = new AtomicReference<>();
        AtomicReference<StateMachine> smAtom = new AtomicReference<>();
        AtomicReference<Button> zoomBtnAtom = new AtomicReference<>();
        AtomicReference<Button> eatBtnAtom = new AtomicReference<>();
        AtomicReference<Button> resetBtnAtom = new AtomicReference<>();
        AtomicReference<Button> upBtnAtom = new AtomicReference<>();
        AtomicReference<Button> downBtnAtom = new AtomicReference<>();
        AtomicReference<Button> leftBtnAtom = new AtomicReference<>();
        AtomicReference<Button> rightBtnAtom = new AtomicReference<>();
        AtomicReference<TextView> zoomAtom = new AtomicReference<>();
        AtomicReference<TextView> moveAtom = new AtomicReference<>();
        AtomicReference<TextView> energyAtom = new AtomicReference<>();
        AtomicReference<TextView> storesAtom = new AtomicReference<>();
        AtomicReference<TextView> foodAtom = new AtomicReference<>();
        act.getScenario().onActivity(act -> {
            gameAtom.set(act.getGame());
            smAtom.set(act.getStateMachine());

            zoomBtnAtom.set(act.findViewById(R.id.zoomBtn));
            eatBtnAtom.set(act.findViewById(R.id.eatBtn));
            resetBtnAtom.set(act.findViewById(R.id.resetBtn));
            upBtnAtom.set(act.findViewById(R.id.upBtn));
            downBtnAtom.set(act.findViewById(R.id.downBtn));
            leftBtnAtom.set(act.findViewById(R.id.leftbtn));
            rightBtnAtom.set(act.findViewById(R.id.rightBtn));

            zoomAtom.set(act.findViewById(R.id.zoom));
             moveAtom.set(act.findViewById(R.id.moves));
            energyAtom.set(act.findViewById(R.id.energy));
            storesAtom.set(act.findViewById(R.id.stores));
            foodAtom.set(act.findViewById(R.id.food));
        });

        g = gameAtom.get();
        sm = smAtom.get();
        zoomBtn=zoomBtnAtom.get();
        eatBtn=eatBtnAtom.get();
        resetBtn=resetBtnAtom.get();
        upBtn=upBtnAtom.get();
        downBtn=downBtnAtom.get();
        leftBtn=leftBtnAtom.get();
        rightBtn=rightBtnAtom.get();

        zoomView = zoomAtom.get();
        moveView = moveAtom.get();
        energyView = energyAtom.get();
        storesView = storesAtom.get();
        foodView = foodAtom.get();
    }

    @Test
    public void allCorrectButtonsAndIdsThere() {
        init();
        boolean notSet = g == null;
        notSet |= zoomBtn == null;
        notSet |= eatBtn == null;
        notSet |= resetBtn == null;
        notSet |= upBtn == null;
        notSet |= downBtn == null;
        notSet |= leftBtn == null;
        notSet |= rightBtn == null;

        notSet |= zoomView == null;
        notSet |= moveView == null;
        notSet |= energyView == null;
        notSet |= storesView == null;
        notSet |= foodView == null;

        assertFalse(notSet);
    }

    public void check_number_match(){
        assertEquals(Integer.parseInt(zoomView.getText().toString()), g.getZoomsLeft());
        assertEquals(Integer.parseInt(moveView.getText().toString()), g.getMoves());
        assertEquals(Integer.parseInt(energyView.getText().toString()), g.getEnergy());
        assertEquals(Integer.parseInt(storesView.getText().toString()), g.getHomeStores());
        assertEquals(Integer.parseInt(foodView.getText().toString()), g.getFood());
    }


    @Test
    public void eat_correct() {
        init();
        //no food, no effect
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(0, g.getFood());
        assertEquals(START_ENERGY, g.getEnergy());

        //pickup food, and eat
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(4, g.getFood());
        assertEquals(START_ENERGY+EAT_ENERGY-1, g.getEnergy());

        // check for max energy
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(MAX_ENERGY, g.getEnergy());

        //eat while full tosses food
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(1, g.getFood());
        assertEquals(MAX_ENERGY, g.getEnergy());
        check_number_match();
    }
    @Test
    public void allStartingValuesStillCorrect() {
        init();
        assertEquals(g.getHomeStores(), STORED_FOOD);
        assertEquals(g.getEnergy(), START_ENERGY);
        assertEquals(g.getFood(), START_FOOD_IN_POUCHES);
        assertEquals(g.getMoves(), START_MOVE_CNT);
        assertEquals(g.getZoomsLeft(), START_ZOOMS_LEFT);
        assertEquals(g.getPlayerLocation().x, X);
        assertEquals(g.getPlayerLocation().y, Y);
    }


    @Test
    public void test_bars() {
        init();
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.upBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        assertEquals(BARS_LIMIT, g.getFood());
        check_number_match();
    }


    @Test
    public void move_values_correct() {
        init();
        onView(withId(R.id.rightBtn)).perform(click());
        assertEquals(g.getMoves(), 1);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(1, g.getPlayerLocation().x );
        assertEquals(0, g.getPlayerLocation().y);

        onView(withId(R.id.leftbtn)).perform(click());
        assertEquals(2, g.getMoves());
        assertEquals( START_ENERGY-g.getMoves() * MOVE_ENERGY, g.getEnergy());
        assertEquals(g.getPlayerLocation().x, 0);
        assertEquals(g.getPlayerLocation().y, 0);

        onView(withId(R.id.downBtn)).perform(click());
        assertEquals(g.getMoves(), 3);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(g.getPlayerLocation().x, 0);
        assertEquals(g.getPlayerLocation().y, 1);

        onView(withId(R.id.upBtn)).perform(click());
        assertEquals(g.getMoves(), 4);
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(g.getPlayerLocation().x, 0);
        assertEquals(g.getPlayerLocation().y, 0);


        onView(withId(R.id.upBtn)).perform(click());
        assertEquals(4, g.getMoves());
        assertEquals(g.getEnergy(), START_ENERGY-g.getMoves() * MOVE_ENERGY);
        assertEquals(g.getPlayerLocation().x, 0);
        assertEquals(g.getPlayerLocation().y, 0);

        onView(withId(R.id.leftbtn)).perform(click());
        assertEquals(4, g.getMoves());
        assertEquals( START_ENERGY-g.getMoves() * MOVE_ENERGY, g.getEnergy());
        assertEquals(g.getPlayerLocation().x, 0);
        assertEquals(g.getPlayerLocation().y, 0);

        check_number_match();
    }


    @Test
    public void test_home() {
        init();
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.upBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());

        //need energy to make it
        onView(withId(R.id.eatBtn)).perform(click());

        //finish trip
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());

        assertEquals(4, g.getHomeStores());
        assertEquals(0, g.getFood());
        check_number_match();
    }


    @Test
    public void food_correct() {
        init();
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());

        assertEquals(5, g.getFood() );

        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());

        assertEquals(10, g.getFood() );

        check_number_match();
    }





    @Test
    public void reset_correct() {
        init();
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.leftbtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());

        onView(withId(R.id.resetBtn)).perform(click());

        assertEquals(STORED_FOOD, g.getHomeStores());
        assertEquals(START_ENERGY, g.getEnergy());
        assertEquals(START_FOOD_IN_POUCHES, g.getFood());
        assertEquals(START_MOVE_CNT, g.getMoves());
        assertEquals(START_ZOOMS_LEFT, g.getZoomsLeft());
        assertEquals(X, g.getPlayerLocation().x);
        assertEquals(Y, g.getPlayerLocation().y);

        check_number_match();
    }


}