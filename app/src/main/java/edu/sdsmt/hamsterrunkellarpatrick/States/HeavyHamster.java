package edu.sdsmt.hamsterrunkellarpatrick.States;

import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.Game;
import edu.sdsmt.hamsterrunkellarpatrick.MainActivity;
import edu.sdsmt.hamsterrunkellarpatrick.State;
import edu.sdsmt.hamsterrunkellarpatrick.StateMachine;
import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;

public class HeavyHamster extends State {

    public HeavyHamster(StateMachine stateMachine, GameView gameView, Game game, MainActivity mainActivity) {
        super(stateMachine, gameView, game, mainActivity);
    }

    /**
     * Description: Have the hamster eat and check if it needs to be a base hamster
     * */
    @Override
    public void eatTask() {
        game.eat();

        //if the hamster has less than 15 food units, make it a base hamster
        if (game.getFood() < 15) {
            stateMachine.setState(StateMachine.StateEnum.BaseHamster);
        }
    }

    @Override
    public void endTask() {
        //do nothing
    }

    /**
     * Description: Move the hamster and check if it has won or lost
     *
     * @param move - the direction the hamster is moving
     * */
    @Override
    public void maintenanceTask(Point move) {
        //move in the amount in each direction passed in
        game.move(move.x, move.y);
        //pickup on whatever tile was landed on
        game.pickup();

        //If the player lost or won, set the ended state
        if (game.isLost() || game.isWon()) {
            stateMachine.setState(StateMachine.StateEnum.EndedGame);
        }
    }

    /**
     * Description: Set the energy needed to move to 2
     * */
    @Override
    public void startTask() {
        //GRADING: ENERGY
        game.setMoveEnergy(2);
    }

    /**
     * Description: Perform zoom pressed actions
     * */
    @Override
    public void zoomPressed() {
        //if the hamster has zoom power ups left
        if (game.getZoomsLeft() > 0) {

            //use a zoom and set the state to zooming
            game.useZoom();
            stateMachine.setState(StateMachine.StateEnum.ZoomingHamster);
        }
    }
}
