package edu.sdsmt.hamsterrunkellarpatrick.States;

import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.Game;
import edu.sdsmt.hamsterrunkellarpatrick.MainActivity;
import edu.sdsmt.hamsterrunkellarpatrick.State;
import edu.sdsmt.hamsterrunkellarpatrick.StateMachine;
import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;

public class ZoomingHamster extends State {

    public ZoomingHamster(StateMachine stateMachine, GameView gameView, Game game, MainActivity mainActivity) {
        super(stateMachine, gameView, game, mainActivity);
    }

    @Override
    public void endTask() {
        //do nothing
    }

    /**
     * Description: Perform move action for a zooming hamster
     *
     * @param move - the direction the hamster is moving
     * */
    @Override
    public void maintenanceTask(Point move) {

        //if no zooms left and have less than 15 food
        //GRADING: TO_ZOOM
        if ((game.getZoomMovesLeft() <= 0) && (game.getFood() < 15)) {

            //move as a base hamster
            game.setMoveEnergy(1);
            game.move(move.x, move.y);
            game.pickup();

            //check if won or lost
            if (game.isLost() || game.isWon()) {
                stateMachine.setState(StateMachine.StateEnum.EndedGame);
            }
            //if not, move to base state
            else {
                stateMachine.setState(StateMachine.StateEnum.BaseHamster);
            }
            //if no zooms left and have less than 15 food
        } else if ((game.getZoomMovesLeft() <= 0) && (game.getFood() >= 15)) {

            //move as heavy hamster
            game.move(move.x, move.y);
            game.pickup();

            //check if won or lost
            if (game.isLost() || game.isWon()) {
                stateMachine.setState(StateMachine.StateEnum.EndedGame);
            }
            //if not, move to heavy state
            else {
                stateMachine.setState(StateMachine.StateEnum.HeavyHamster);
            }
        } else {
            //move two spaces
            game.move(move.x * 2, move.y * 2);
            //decrement how many zooms the hamster has
            game.setZoomMovesLeft(game.getZoomMovesLeft() - 1);

            //check if won or lost
            if (game.isLost() || game.isWon()) {
                stateMachine.setState(StateMachine.StateEnum.EndedGame);
            }
        }
    }

    /**
     * Description: Start task for zooming hamster
     * */
    @Override
    public void startTask() {

        //set the game to take 2 units of energy to move
        //GRADING: ENERGY
        game.setMoveEnergy(2);

        //Only add zoom moves if hamster has none
        if (game.getZoomMovesLeft() == 0) {
            game.setZoomMovesLeft(2);
        }
    }

    /**
     * Description: zoom button pressed for zooming hamster
     * */
    @Override
    public void zoomPressed() {
        //if the player has zoom power ups left, use one and set how many zoom moves the hamster has
        if (game.getZoomsLeft() > 0) {
            game.useZoom();
            game.setZoomMovesLeft(2);
        }
    }

}
