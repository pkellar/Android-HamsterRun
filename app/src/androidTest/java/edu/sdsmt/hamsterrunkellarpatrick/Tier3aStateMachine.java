package edu.sdsmt.hamsterrunkellarpatrick;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.widget.Button;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

import edu.sdsmt.hamsterrunkellarpatrick.States.BaseHamster;
import edu.sdsmt.hamsterrunkellarpatrick.States.EndedGame;
import edu.sdsmt.hamsterrunkellarpatrick.States.HeavyHamster;
import edu.sdsmt.hamsterrunkellarpatrick.States.ZoomingHamster;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Tier3aStateMachine {

    //starting values
    static final int STORED_FOOD = 0;
    static final int START_ENERGY = 10;
    static final int START_FOOD_IN_POUCHES = 0;
    static final int START_MOVE_CNT = 0;
    static final int START_ZOOMS_LEFT = 0;
    static final int X = 0;
    static final int Y = 0;


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

    @Test
    public void base_to_heavy_and_back() {
        init();
        assertEquals(BaseHamster.class.getName(), sm.getCurrentStateName());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.leftbtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        String t= HeavyHamster.class.getName();
        String t2 = sm.getCurrentStateName();
        assertEquals(HeavyHamster.class.getName(), sm.getCurrentStateName());

        //check energy levels
        onView(withId(R.id.leftbtn)).perform(click());
        assertEquals(2, g.getEnergy());
        onView(withId(R.id.rightBtn)).perform(click());
        assertEquals(0, g.getEnergy());

        //move back to base
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(5, g.getEnergy());
        onView(withId(R.id.leftbtn)).perform(click());
        assertEquals(3, g.getEnergy());
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(HeavyHamster.class.getName(), sm.getCurrentStateName());
        onView(withId(R.id.eatBtn)).perform(click());
        assertEquals(BaseHamster.class.getName(), sm.getCurrentStateName());
        onView(withId(R.id.leftbtn)).perform(click());
        assertEquals(14, g.getEnergy());

        check_number_match();
    }

    @Test
    public void base_to_zoom_and_back() {
        init();
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.leftbtn)).perform(click());
        onView(withId(R.id.leftbtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());


        onView(withId(R.id.zoomBtn)).perform(click());
        assertEquals(ZoomingHamster.class.getName(), sm.getCurrentStateName());

        //need to eat to keep going
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.upBtn)).perform(click());
        assertEquals(10, g.getEnergy());

        onView(withId(R.id.zoomBtn)).perform(click());
        assertEquals(ZoomingHamster.class.getName(), sm.getCurrentStateName());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        assertEquals(ZoomingHamster.class.getName(), sm.getCurrentStateName());
        onView(withId(R.id.rightBtn)).perform(click());

        assertEquals(BaseHamster.class.getName(), sm.getCurrentStateName());
    }

    public void check_number_match() {
        assertEquals(Integer.parseInt(zoomView.getText().toString()), g.getZoomsLeft());
        assertEquals(Integer.parseInt(moveView.getText().toString()), g.getMoves());
        assertEquals(Integer.parseInt(energyView.getText().toString()), g.getEnergy());
        assertEquals(Integer.parseInt(storesView.getText().toString()), g.getHomeStores());
        assertEquals(Integer.parseInt(foodView.getText().toString()), g.getFood());
    }

    @Test
    public void heavy_to_zoom_and_back() {
        init();
        onView( withId(R.id.downBtn)).perform(click());
        onView( withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.leftbtn)).perform(click());
        onView( withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.leftbtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.eatBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        assertEquals(HeavyHamster.class.getName(), sm.getCurrentStateName());

        onView(withId(R.id.zoomBtn)).perform(click());
        assertEquals(ZoomingHamster.class.getName(), sm.getCurrentStateName());
        onView(withId(R.id.leftbtn)).perform(click());
        onView(withId(R.id.upBtn)).perform(click());
        onView(withId(R.id.upBtn)).perform(click());
        assertEquals(HeavyHamster.class.getName(), sm.getCurrentStateName());
    }

    //Initialize to have access to underlying game, state machine, and distract button
    private void init() {
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
        zoomBtn = zoomBtnAtom.get();
        eatBtn = eatBtnAtom.get();
        resetBtn = resetBtnAtom.get();
        upBtn = upBtnAtom.get();
        downBtn = downBtnAtom.get();
        leftBtn = leftBtnAtom.get();
        rightBtn = rightBtnAtom.get();

        zoomView = zoomAtom.get();
        moveView = moveAtom.get();
        energyView = energyAtom.get();
        storesView = storesAtom.get();
        foodView = foodAtom.get();

    }

    @Test
    public void test_caught_lost() {
        init();
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());

        assertTrue(g.isLost());
        // Assert
        assertEquals(EndedGame.class.getName(), sm.getCurrentStateName());
        check_number_match();
    }

    @Test
    public void test_energy_lost() {
        init();
        boolean right = true;
        for(int i = 0; i <= START_ENERGY ; i++) {
            if(right)
                onView(withId(R.id.rightBtn)).perform(click());
            else
                onView(withId(R.id.leftbtn)).perform(click());
            right = !right;
        }

        assertTrue(g.isLost());
        // Assert
        assertEquals(EndedGame.class.getName(), sm.getCurrentStateName());
        check_number_match();
    }

    @Test
    public void test_win()  {
        init();
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.upBtn)).perform(click());

        for (int i = 0; i < 4; i++) {
            //need energy to make it
            onView(withId(R.id.eatBtn)).perform(click());
            onView(withId(R.id.eatBtn)).perform(click());

            onView(withId(R.id.downBtn)).perform(click());
            onView(withId(R.id.downBtn)).perform(click());
            onView(withId(R.id.downBtn)).perform(click());
            onView(withId(R.id.downBtn)).perform(click());
            onView(withId(R.id.rightBtn)).perform(click());

            onView(withId(R.id.leftbtn)).perform(click());
            onView(withId(R.id.upBtn)).perform(click());
            onView(withId(R.id.upBtn)).perform(click());
            onView(withId(R.id.upBtn)).perform(click());
            onView(withId(R.id.upBtn)).perform(click());
        }

        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());

        assertTrue(g.isWon());
        // Assert
        assertEquals(EndedGame.class.getName(), sm.getCurrentStateName());
        check_number_match();
    }
}