package edu.sdsmt.hamsterrunkellarpatrick;

import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.States.BaseHamster;
import edu.sdsmt.hamsterrunkellarpatrick.States.EndedGame;
import edu.sdsmt.hamsterrunkellarpatrick.States.HeavyHamster;
import edu.sdsmt.hamsterrunkellarpatrick.States.ZoomingHamster;
import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;


/*
 * Author: Patrick Kellar
 * Description: Handles the core part of the state machine
 * */
public class StateMachine {
    private final Game game;
    private final MainActivity mainActivity;
    private final State[] stateArray;
    private StateEnum state = StateEnum.BaseHamster;
    public enum StateEnum {BaseHamster, HeavyHamster, ZoomingHamster, EndedGame}

    StateMachine(GameView gameView, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.game = mainActivity.getGame();

        stateArray = new State[]{
                new BaseHamster(this, gameView, game, mainActivity),
                new HeavyHamster(this, gameView, game, mainActivity),
                new ZoomingHamster(this, gameView, game, mainActivity),
                new EndedGame(this, gameView, game, mainActivity),
        };
    }

    /**
     * Description: return the index of the current state in the StateEnum
     *
     * @return the index of the current state
     * * */
    public int getCurrentStateInt() {
        return state.ordinal();
    }

    /**
     * Description: Get the name of the current state
     *
     * @return the name of the current state
     * */
    public String getCurrentStateName() {
        return stateArray[state.ordinal()].getClass().getName();
    }

    /**
     * Description: perform the eat task of the current state
     * */
    public void onEat() {
        stateArray[state.ordinal()].eatTask();
    }

    /**
     * Description: perform the move task of the current state
     *
     * @param move the direction the player is moving
     * */
    public void onUpdate(Point move) {
        stateArray[state.ordinal()].maintenanceTask(move);
    }

    /**
     * Description: Reset the game and update the UI
     * */
    public void reset() {
        game.reset();
        mainActivity.updateUI();
    }

    /**
     * Description: set the state to what it was before rotate
     *
     * @param state the saved state of the hamster
     * */
    public void restoreState(int state) {
        setState(StateEnum.values()[state]);
    }

    /**
     * Description: Run the end task for the previous state, set the new state, run its start tasks
     *
     * @param state the new state the state machine is in
     * */
    public void setState(StateEnum state) {
        //end tasks of the previous state
        stateArray[this.state.ordinal()].endTask();

        //set the new state
        this.state = state;


        //start tasks of the new state
        stateArray[this.state.ordinal()].startTask();
    }

    /**
     * Description: Perform the zoom button click action in the current state
     * */
    public void zoomPressed() {
        stateArray[state.ordinal()].zoomPressed();
    }

}
