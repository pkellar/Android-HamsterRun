package edu.sdsmt.hamsterrunkellarpatrick.States;

import android.app.AlertDialog;
import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.Game;
import edu.sdsmt.hamsterrunkellarpatrick.MainActivity;
import edu.sdsmt.hamsterrunkellarpatrick.State;
import edu.sdsmt.hamsterrunkellarpatrick.StateMachine;
import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;

public class EndedGame extends State {

    public EndedGame(StateMachine stateMachine, GameView gameView, Game game, MainActivity mainActivity) {
        super(stateMachine, gameView, game, mainActivity);
    }

    /**
     * Description: Restart the system
     * */
    @Override
    public void endTask() {
        stateMachine.reset();
    }

    @Override
    public void maintenanceTask(Point move) {
        //do nothing
    }

    /**
     * Description: Start task for ended game
     * */
    @Override
    public void startTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        //check if hamster lost from energy loss
        if (game.getEnergy() < 0) {
            builder.setTitle("Lost dialog");

            builder.setMessage("You ran out of energy.");
        }
        //check if hamster won
        else if (game.getHomeStores() >= 15) {
            builder.setTitle("Won dialog");

            builder.setMessage("You were able to fill the home with more 15 or more food.");
        }
        //else lost from pickup
        else {
            builder.setTitle("Lost dialog");

            builder.setMessage("You were picked up by a person.");
        }

        //Once reset is clicked, make a base hamster
        builder.setPositiveButton("Reset", (dialog, id) ->
                //GRADING: RESET
                stateMachine.setState(StateMachine.StateEnum.BaseHamster));

        //display the dialog
        //GRADING:DIALOG
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
