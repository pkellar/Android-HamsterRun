package edu.sdsmt.hamsterrunkellarpatrick;

import android.graphics.Point;

import edu.sdsmt.hamsterrunkellarpatrick.Model.Bars;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Foodpile;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Hamster;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Home;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Person;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Tile;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Tube;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Zoom;

/*
 * Author: Patrick Kellar
 * Description: Model class of the game
 * */
public class Game {

    private static final Point[] foodLocations = {new Point(3, 0), new Point(0, 1),
            new Point(2, 2), new Point(0, 3)};
    private final Tile[][] tiles;
    private Hamster hamster = new Hamster();
    private Point location = new Point(0, 0);
    private int moves = 0;

    public Game() {
        tiles = new Tile[5][5];

        //Row 1
        tiles[0][0] = new Tile(new Tube());
        tiles[1][0] = new Tile(new Tube());
        tiles[2][0] = new Tile(new Bars());
        tiles[3][0] = new Tile(new Foodpile(10));
        tiles[4][0] = new Tile(new Tube());

        //Row 2
        tiles[0][1] = new Tile(new Foodpile(1));
        tiles[1][1] = new Tile(new Tube());
        tiles[2][1] = new Tile(new Zoom());
        tiles[3][1] = new Tile(new Tube());
        tiles[4][1] = new Tile(new Tube());

        //Row 3
        tiles[0][2] = new Tile(new Tube());
        tiles[1][2] = new Tile(new Tube());
        tiles[2][2] = new Tile(new Foodpile(5));
        tiles[3][2] = new Tile(new Tube());
        tiles[4][2] = new Tile(new Tube());

        //Row 4
        tiles[0][3] = new Tile(new Foodpile(2));
        tiles[1][3] = new Tile(new Tube());
        tiles[2][3] = new Tile(new Person());
        tiles[3][3] = new Tile(new Bars());
        tiles[4][3] = new Tile(new Bars());

        //Row 5
        tiles[0][4] = new Tile(new Zoom());
        tiles[1][4] = new Tile(new Tube());
        tiles[2][4] = new Tile(new Bars());
        tiles[3][4] = new Tile(new Tube());
        tiles[4][4] = new Tile(new Home());

    }

    /**
     * Description: Has the hamster eat
     * */
    public void eat() {
        hamster.eat();
    }

    /**
     * Description: Gets the hamster's energy
     *
     * @return hamster's energy
     * */
    public int getEnergy() {
        return hamster.getEnergy();
    }

    /**
     * Description: Gets the hamster's food
     *
     * @return hamster's food
     * */
    public int getFood() {
        return hamster.getFood();
    }

    /**
     * Description: Gets the locations of the food piles
     *
     * @return array of coordinates
     * */
    public Point[] getFoodLocations() {
        return foodLocations;
    }

    /**
     * Description: Gets the hamster's energy
     *
     * @return array of food left in each food pile
     * */
    public int[] getFoodUnits() {
        int[] foodUnits = new int[foodLocations.length];

        //iterate through each food pile location and gets how many units of food are left in each
        for (int i = 0; i < foodLocations.length; i++) {
            foodUnits[i] = tiles[foodLocations[i].x][foodLocations[i].y].getFoodUnits();
        }

        return foodUnits;
    }

    /**
     * Description: Gets how much food is stored at the home tile
     *
     * @return food stored at home
     * */
    public int getHomeStores() {
        return tiles[4][4].getHomeStores();
    }

    /**
     * Description: Gets the number of moves made
     *
     * @return moves made
     * */
    public int getMoves() {
        return moves;
    }

    /**
     * Description: Gets the players current location
     *
     * @return coordinates of the player
     * */
    public Point getPlayerLocation() {
        return location;
    }

    /**
     * Description: Gets the class of a tile
     *
     * @return Class of a tile
     * */
    public Class<?> getTileClass(int x, int y) {
        return tiles[x][y].getTileClass();
    }

    /**
     * Description: Gets the hamster's zoom moves it has left to make
     *
     * @return hamster's zoom moves remaining
     * */
    public int getZoomMovesLeft() {
        return hamster.getZoomMovesLeft();
    }

    /**
     * Description: Gets the hamster's zoom power up count
     *
     * @return hamster's zoom power ups
     * */
    public int getZoomsLeft() {
        return hamster.getZoomsLeft();
    }

    /**
     * Description: Check if the hamster has lost
     *
     * @return hamster's lost state
     * */
    public boolean isLost() {
        return hamster.isLost();
    }

    /**
     * Description: Check if the hamster has won
     *
     * @return hamster's won state
     * */
    public boolean isWon() {
        return hamster.isWon();
    }

    /**
     * Description: Move the hamster by the x and y value passed int
     *
     * @param x How to move the hamster in the x direction
     * @param y How to move the hamster in the y direction
     * */
    public void move(int x, int y) {
        //Check that the location is in bounds
        if ((location.x + x) < 5 && (location.x + x) > -1) {
            if ((location.y + y) < 5 && (location.y + y) > -1) {

                //Check that the hamster has energy left
                if (hamster.getEnergy() >= 0) {
                    //Remove energy needed to move
                    hamster.removeMoveEnergy();
                    //increment move counter
                    moves += 1;

                    //update x and y location
                    location.x += x;
                    location.y += y;

                    //have the new tile perform its move function
                    tiles[location.x][location.y].move(hamster);

                    //Check if the hamster has lost
                    hamster.lostCheck();
                }
            }
        }
    }

    /**
     * Description: Tile performs its pickup action
     * */

    public void pickup() {
        //have the tile perform its pickup action and pass in the hamster
        tiles[location.x][location.y].pickup(hamster);
    }

    /**
     * Description: Restarts the game to its initial state
     * */
    public void reset() {
        //set the location back to (0,0)
        location = new Point(0, 0);

        //no moves
        moves = 0;

        //make new hamster
        hamster = new Hamster();

        //reset the food piles and home to their inital state
        tiles[3][0] = new Tile(new Foodpile(10));
        tiles[0][1] = new Tile(new Foodpile(1));
        tiles[2][2] = new Tile(new Foodpile(5));
        tiles[0][3] = new Tile(new Foodpile(2));
        tiles[4][4] = new Tile(new Home());
    }

    /**
     * Description: Set the energy of the hamster
     *
     * @param energy the new energy of the hamster
     * */
    public void setEnergy(int energy) {
        hamster.setEnergy(energy);
    }

    /**
     * Description: Set how much food the hamster has
     *
     * @param food the new food count of the hamster
     * */
    public void setFood(int food) {
        hamster.setFood(food);
    }

    /**
     * Description: Set how much food is in each pile of food
     *
     * @param foodUnits how many units in each food pile
     * */
    public void setFoodUnits(int[] foodUnits) {
        //for each food location, set the food units
        for (int i = 0; i < foodLocations.length; i++) {
            tiles[foodLocations[i].x][foodLocations[i].y].setFoodUnits(foodUnits[i]);
        }
    }

    /**
     * Description: Setting the amount of food storesd at home
     *
     * @param foodDeposited how many units being deposited at home
     * */
    public void setHomeStores(int foodDeposited) {
        tiles[4][4].setHomeStores(foodDeposited);
    }

    /**
     * Description: Set the lost state of the hamster
     *
     * @param lostState boolean if the hamster has lost
     * */
    public void setLost(boolean lostState) {
        hamster.setLost(lostState);
    }

    /**
     * Description: Set how much energy it takes the hamster to move
     *
     * @param moveEnergy count of energy it takes to move
     * */
    public void setMoveEnergy(int moveEnergy) {
        hamster.setMoveEnergy(moveEnergy);
    }

    /**
     * Description: Set home many moves have happened in the game
     *
     * @param moves count of energy it takes to move
     * */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * Description: Set the location of the hamster
     *
     * @param location Point of the coordinates of the hamster
     * */
    public void setPlayerLocation(Point location) {
        this.location = location;
    }

    /**
     * Description: Set the won state of the hamster
     *
     * @param wonState boolean if the hamster has won
     * */
    public void setWon(boolean wonState) {
        hamster.setWon(wonState);
    }

    /**
     * Description: Set how many more moves the hamster can move two tiles and not pickup anything
     *
     * @param zoomMovesLeft count of the amount of time a hamster can move with a zoom ability
     * */
    public void setZoomMovesLeft(int zoomMovesLeft) {
        hamster.setZoomMovesLeft(zoomMovesLeft);
    }

    /**
     * Description: Set how much zoom power the hamster has
     *
     * @param zoomPower how many zoom power ups the player has
     * */
    public void setZooms(int zoomPower) {
        hamster.setZooms(zoomPower);
    }

    /**
     * Description: Use of of the hamster's zoom powers
     * */
    public void useZoom() {
        hamster.useZoom();
    }
}
