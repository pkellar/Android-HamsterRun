package edu.sdsmt.hamsterrunkellarpatrick.States;

import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.Game;
import edu.sdsmt.hamsterrunkellarpatrick.MainActivity;
import edu.sdsmt.hamsterrunkellarpatrick.State;
import edu.sdsmt.hamsterrunkellarpatrick.StateMachine;
import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;

public class BaseHamster extends State {

    public BaseHamster(StateMachine stateMachine, GameView gameView, Game game, MainActivity mainActivity) {
        super(stateMachine, gameView, game, mainActivity);
    }

    @Override
    public void endTask() {
        //do nothing
    }

    /**
     * Description: Perform move action for a base hamster
     *
     * @param move - the direction the hamster is moving
     * */
    @Override
    public void maintenanceTask(Point move) {
        //move the hamster and perform pickup
        game.move(move.x, move.y);
        game.pickup();

        //check if the hamster won or lost
        if (game.isLost() || game.isWon()) {
            stateMachine.setState(StateMachine.StateEnum.EndedGame);
        }
        //If the hamster has more than 15 food, make a heavy hamster
        else if (game.getFood() >= 15) {
            //GRADING: TO_HEAVY
            stateMachine.setState(StateMachine.StateEnum.HeavyHamster);
        }
    }

    /**
     * Description: Set the energy needed to move to 1
     * */
    @Override
    public void startTask() {
        //GRADING: ENERGY
        game.setMoveEnergy(1);
    }

    /**
     * Description: Start task for zooming hamster
     * */
    @Override
    public void zoomPressed() {
        //if the player has zoom power ups left, use one and the hamster to be zooming
        if (game.getZoomsLeft() > 0) {
            game.useZoom();
            stateMachine.setState(StateMachine.StateEnum.ZoomingHamster);
        }
    }
}
