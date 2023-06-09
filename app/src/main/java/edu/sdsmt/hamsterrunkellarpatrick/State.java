package edu.sdsmt.hamsterrunkellarpatrick;

import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;

/*
 * Author: Patrick Kellar
 * Description: Abstract class of the states of the system
 * */
public abstract class State {
    final protected StateMachine stateMachine;
    final protected GameView gameView;
    final protected Game game;
    final protected MainActivity mainActivity;

    public State(StateMachine stateMachine, GameView gameView, Game game, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.stateMachine = stateMachine;
        this.gameView = gameView;
        this.game = game;
    }

    /**
     * Description: Has the game perform an eat action
     * */
    public void eatTask() {
        game.eat();
    }

    /**
     * Description: Performs ending tasks for leaving a state
     * */
    public abstract void endTask();

    /**
     * Description: Performs movement task for a certain state
     *
     * @param move - the coordinates the player is moving
     * */
    public abstract void maintenanceTask(Point move);

    /**
     * Description: Performs starting tasks for entering a state
     * */
    public abstract void startTask();

    /**
     * Description: Has the state deal with a zoom button being pressed
     * */
    public void zoomPressed() {}
}
