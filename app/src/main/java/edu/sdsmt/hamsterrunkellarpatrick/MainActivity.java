package edu.sdsmt.hamsterrunkellarpatrick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.sdsmt.hamsterrunkellarpatrick.View.GameView;

/*
* Program Name: Android
* Author: Patrick Kellar
* Class: CSC-468-M01 Spring 2023
* Description: This is a hamster grid game.
* Last tier passed: Finished all tiers

_X_ Followed the class OOP diagram
_X_ *Grading tags completed


        Tier 1: Model		50
        Move test 			_X_
        Food test			_X_
        Eat test 			_X_
        Home test 			_X_
        Zoom pickup test	_X_
        Bar test 			_X_
        Caught test 		_X_
        No energy test		_X_
        Win test 			_X_

        Tier 2: Connect Views		22
        All views present test	 	_X_
        Starting values test pass	_X_
        Move test 	 				_X_
        Food test	 				_X_
        Eat test  					_X_
        Bar test	 				_X_
        Home test	 				_X_
        Reset test	 				_X_

        Tier 3a: State Machine/Event Rules	34
        Framework there	 			_X_
        Base to heavy*	 			_X_
        Heavy to zoom* 				_X_
        Base to zoom* 				_X_
        Caught*	 					_X_
        No energy*	 				_X_
        Win*	 					_X_
        Reset on close ***	 		_X_

        Tier 3b: Floating Action	 		_X_
        All buttons there 		 		_X_
        Icons set and distinguishable	_X_
        Opens/closes properly 	 		_X_
        Tribble color updated.	 		_X_

        Tier 3c: Layout **	26
        Custom’s View’s aspect ratio constant			_X_
        Relative size of objects in view maintained 	_X_
        Works in required screen sizes 	 				_X_


        Tier 3d: Rotation		20
        Required state saved on rotation 	 		_X_

        Tier 4: Extensions		30

        Extension 1: 1c 5pts Add another 1+ player appearance options:
        * I added a yellow player appearance option onto the red, blue, and black options I already had.
        * This will be preserved across rotations like the other color. Can test by clicking the FAB
        * and then selecting the hamster that is yellow colored.

        Extension 2: 1g 10pts Indicated the number of food units left in an area:
        * This can be seen by looking at the information under the text that says "Food left in piles".
        * Bellow this is a list of each food piles coordinates on the board and how many units of food
        * are left in it. If you pickup food from one of these piles, it will be reflected in this information.

        Extension 3: 1j 5pts Put red boarder on when zoom power up is active:
        * This can be tested by going to a zoom tile and picking up a zoom power up. Then you can click
        * the "zoom" button. This will put the border on while the user has its two zoom moves.

        Extension 4: 5a 5pts Disable zoom button if there are no zoom power ups:
        * This can be tested by the zoom button not being clickable whenever there are no zoom powers.
        * If you go to a zoom tile and get a power up, the button should then be clickable.

        Extension 5: 5b 5pts Save FAB open/close on rotation:
        * This can be tested by opening the FAB menu and then rotating the screen.
 */

//Saves and restores state
//Controls main activity area
public class MainActivity extends AppCompatActivity {

    /*
     * Constant strings for everything stored in the bundle
     */
    private static final String GAME_FOOD = "gameFood";
    private static final String GAME_ZOOM = "gameZoom";
    private static final String GAME_ENERGY = "gameEnergy";
    private static final String GAME_MOVES = "gameMoves";
    private static final String GAME_HOME_FOOD = "gameHomeFood";
    private static final String SAVED_STATE = "savedState";
    private static final String GAME_LOCATION = "gameLocation";
    private static final String FOODPILE_UNITS = "foodpileUnits";
    private static final String GAME_WON = "gameWon";
    private static final String GAME_LOST = "gameLost";
    private static final String GAME_ZOOM_MOVES_LEFT = "gameZoomMovesLeft";
    private static final String FAB_STATE = "FABstate";
    private static final String HAMSTER_COLOR = "hamsterColor";
    private final Game game = new Game();
    private TextView energyDisplay;
    private FloatingActionButton fabHamsterBlack;
    private FloatingActionButton fabHamsterBlue;
    private FloatingActionButton fabHamsterRed;
    private FloatingActionButton fabHamsterWhite;
    private TextView foodDisplay;
    private GameView gameView;
    private TextView homeFoodDisplay;
    private Boolean isFABOpen = false;
    private TextView movesDisplay;
    private TextView pileInfoDisplay;
    private StateMachine stateMachine;
    private Button zoomButton;
    private TextView zoomDisplay;

    /**
     * Description: Close the floating action menu
     * */
    private void closeFABMenu() {
        isFABOpen = false;
        fabHamsterBlack.animate().translationY(0);
        fabHamsterBlue.animate().translationY(0);
        fabHamsterRed.animate().translationY(0);
        fabHamsterWhite.animate().translationY(0);

        fabHamsterRed.animate().alpha(0);
        fabHamsterBlue.animate().alpha(0);
        fabHamsterBlack.animate().alpha(0);
        fabHamsterWhite.animate().alpha(0);
    }

    /**
     * Description: Convert dp value to pixels
     *
     * @return pixel value
     * */
    private float DpToPixels(float dp) {
        //get how many pixels there are in a dp
        float pxPerDp = (float) getResources().getDisplayMetrics().densityDpi
                / DisplayMetrics.DENSITY_DEFAULT;

        //make conversion and return the value
        return dp * pxPerDp;
    }

    /**
     * Description: Function handling the eat button
     *
     * @param view - the current view of the system
     * */
    public void eatClick(View view) {
        //Have the state machine run its eating function
        stateMachine.onEat();

        //update the UI
        updateUI();
    }

    public Game getGame() {
        return game;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    /**
     * Description: Function handling a click of one of the four player movement buttons
     *
     * @param view - the current view of the system
     * */
    public void moveClick(View view) {

        //Figure out which button was clicked and tell the state machine the moved direction
        // and have it run its update
        switch (view.getId()) {
            case (R.id.downBtn):

                stateMachine.onUpdate(new Point(0, 1));
                break;
            case (R.id.upBtn):
                stateMachine.onUpdate(new Point(0, -1));
                break;
            case (R.id.rightBtn):
                stateMachine.onUpdate(new Point(1, 0));
                break;
            case (R.id.leftbtn):
                stateMachine.onUpdate(new Point(-1, 0));
                break;
        }
        //update the UI
        updateUI();
    }

    /**
     * Description: Floating action button handler for turning the hamster black
     *
     * @param view - the current view of the system
     * */
    public void onBlack(View view) {
        //Give the hamster a black tint
        gameView.setHamsterColor(Color.BLACK);
    }

    /**
     * Description: Floating action button handler for turning the hamster blue
     *
     * @param view - the current view of the system
     * */
    public void onBlue(View view) {
        //Give the hamster a blue tint
        gameView.setHamsterColor(Color.BLUE);
    }

    /**
     * Description: Floating action button handler for opening or closing the menu
     *
     * @param view - the current view of the system
     * */
    public void onBurst(View view) {
        if (!isFABOpen) {
            showFABMenu();
        } else {
            closeFABMenu();
        }
    }

    /**
     * Description: Creates the system on startup or rotation
     *
     * @param savedInstanceState - the bundle with the saved instance information
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the text views that display info about the game state
        foodDisplay = findViewById(R.id.food);
        zoomDisplay = findViewById(R.id.zoom);
        energyDisplay = findViewById(R.id.energy);
        movesDisplay = findViewById(R.id.moves);
        homeFoodDisplay = findViewById(R.id.stores);
        pileInfoDisplay = findViewById(R.id.pileInfo);
        zoomButton = findViewById(R.id.zoomBtn);

        //if there is saved info, get it and add it to the game
        if (savedInstanceState != null) {
            game.setFood(savedInstanceState.getInt(GAME_FOOD));
            game.setEnergy(savedInstanceState.getInt(GAME_ENERGY));
            game.setHomeStores(savedInstanceState.getInt(GAME_HOME_FOOD));
            game.setMoves(savedInstanceState.getInt(GAME_MOVES));
            game.setZooms(savedInstanceState.getInt(GAME_ZOOM));

            int[] playerLocation = savedInstanceState.getIntArray(GAME_LOCATION);
            game.setPlayerLocation(new Point(playerLocation[0], playerLocation[1]));

            game.setFoodUnits(savedInstanceState.getIntArray(FOODPILE_UNITS));
            game.setWon(savedInstanceState.getBoolean(GAME_WON));
            game.setLost(savedInstanceState.getBoolean(GAME_LOST));
            game.setZoomMovesLeft(savedInstanceState.getInt(GAME_ZOOM_MOVES_LEFT));
            isFABOpen = savedInstanceState.getBoolean(FAB_STATE);
        }

        //Get the game view and give it the current game
        gameView = findViewById(R.id.gameView);
        gameView.setGame(game);

        //Get the floating action buttons with their ids
        fabHamsterBlack = findViewById(R.id.hamsterFABBlack);
        fabHamsterBlue = findViewById(R.id.hamsterFABBlue);
        fabHamsterRed = findViewById(R.id.hamsterFABRed);
        fabHamsterWhite = findViewById(R.id.hamsterFABWhite);

        //Make a new state machine and give it the game view and this activity
        stateMachine = new StateMachine(gameView, this);

        if (savedInstanceState != null) {
            //If there was a saved state, restore it
            stateMachine.restoreState(savedInstanceState.getInt(SAVED_STATE));

            //If there is a saved hamster color, have the game view set it
            if (savedInstanceState.getInt(HAMSTER_COLOR) != -1) {
                gameView.setHamsterColor(savedInstanceState.getInt(HAMSTER_COLOR));
            }
        }

        //update the UI
        updateUI();
    }

    /**
     * Description: Floating action button handler for turning the hamster red
     *
     * @param view - the current view of the system
     * */
    public void onRed(View view) {
        //Give the hamster a red tint
        gameView.setHamsterColor(Color.RED);
    }

    /**
     * Save the instance state
     *
     * @param savedInstanceState Bundle to save to
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //Save all of the game information like the food, energy, moves
        savedInstanceState.putInt(GAME_FOOD, game.getFood());
        savedInstanceState.putInt(GAME_ZOOM, game.getZoomsLeft());
        savedInstanceState.putInt(GAME_ENERGY, game.getEnergy());
        savedInstanceState.putInt(GAME_MOVES, game.getMoves());
        savedInstanceState.putInt(GAME_HOME_FOOD, game.getHomeStores());
        savedInstanceState.putIntArray(GAME_LOCATION, new int[]{game.getPlayerLocation().x,
                game.getPlayerLocation().y});
        savedInstanceState.putBoolean(GAME_LOST, game.isLost());
        savedInstanceState.putBoolean(GAME_WON, game.isWon());
        savedInstanceState.putInt(GAME_ZOOM_MOVES_LEFT, game.getZoomMovesLeft());

        //Get home many food units are left in each foodpile
        savedInstanceState.putIntArray(FOODPILE_UNITS, game.getFoodUnits());
        savedInstanceState.putBoolean(FAB_STATE, isFABOpen);

        //Get the current state of the state machine
        savedInstanceState.putInt(SAVED_STATE, stateMachine.getCurrentStateInt());

        //Get the current color of the hamster
        savedInstanceState.putInt(HAMSTER_COLOR, gameView.getHamsterColor());
    }

    /**
     * Description: Floating action button handler for turning the hamster yellow
     *
     * @param view - the current view of the system
     * */
    public void onYellow(View view) {
        //Give the hamster a yellow tint
        gameView.setHamsterColor(Color.YELLOW);
    }

    /**
     * Description: Reset button handler that will get the system back to its starting state
     *
     * @param view - the current view of the system
     * */
    public void resetClick(View view) {
        //Have the game reset its information
        game.reset();
        //update the UI
        updateUI();
        //Remake the state machine
        stateMachine = new StateMachine(gameView, this);
        //Remove the coloring on the hamster
        gameView.clearHamsterColor();
    }

    /**
     * Description: Show the floating action button menu
     * */
    private void showFABMenu() {
        isFABOpen = true;
        //have the FAB option animate upwards
        fabHamsterBlack.animate().translationY(-DpToPixels(55));
        fabHamsterBlue.animate().translationY(-DpToPixels(105));
        fabHamsterRed.animate().translationY(-DpToPixels(165));
        fabHamsterWhite.animate().translationY(-DpToPixels(220));

        //Make the buttons visible
        fabHamsterBlue.animate().alpha(1f);
        fabHamsterBlack.animate().alpha(1f);
        fabHamsterRed.animate().alpha(1f);
        fabHamsterWhite.animate().alpha(1f);
    }

    /**
     * Description: Update the UI with updated information
     * */
    public void updateUI() {
        //Update the current game information like the food, moves, energy
        foodDisplay.setText(String.valueOf(game.getFood()));
        zoomDisplay.setText(String.valueOf(game.getZoomsLeft()));
        energyDisplay.setText(String.valueOf(game.getEnergy()));
        movesDisplay.setText(String.valueOf(game.getMoves()));
        homeFoodDisplay.setText(String.valueOf(game.getHomeStores()));

        //Get how many food units are left in each food pile
        int[] foodUnits = game.getFoodUnits();
        Point[] foodLocations = game.getFoodLocations();

        StringBuilder pileText = new StringBuilder();

        //Iterate through food pile info and build string that says its coordinates and food left
        for (int i = 0; i < foodLocations.length; i++) {
            pileText.append("(").append(foodLocations[i].x).append(", ").append(foodLocations[i].y)
                    .append(") ->").append(foodUnits[i]).append("\n");
        }
        pileInfoDisplay.setText(pileText);

        //pass the game into the game view
        gameView.setGame(game);

        //if there are zoom moves left, enable the zoom button
        zoomButton.setEnabled(game.getZoomsLeft() > 0);

        //if the floating action menu is open, show it
        if (isFABOpen) {
            showFABMenu();
        }
    }

    /**
     * Description: Zoom button handler that will have the state machine run its zoomPressed function
     *
     * @param view - the current view of the system
     * */
    public void zoomClick(View view) {
        stateMachine.zoomPressed();
        updateUI();
    }
}