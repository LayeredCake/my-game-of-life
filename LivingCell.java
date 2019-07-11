package com.example.mygameoflife;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class LivingCell extends Cell {

    /**
     *  Constant determining how much energy is used by a cell given its action probabilities.
     *  A: 0.05
     *  B: 0.1
     */
    public static float energyPerAction = 0.1f;

    /**
     * Probability of a mutation in a given action probability.
     *  A: 0.0
     *  B: 0.01
     */
    public static float mutationRate = 0.01f;

    /**
     * Whether the cell has been updated this turn.
     */
    protected boolean hasUpdated;

    /**
     * Probability of taking given action.
     */
    protected float copyP;
    protected float moveP;

    /**
     * The grid that the Cell is in.
     */
    protected Cell[][] grid;
    protected int gridH;
    protected int gridW;

    /**
     * The cell's location.
     */
    protected int y;
    protected int x;

    /**
     * The amount of energy stored in the Cell.
     */
    protected double health;

    protected int color;

    protected Random random;

    public LivingCell(float copyP, float moveP, Cell[][] grid, float health, boolean hasUpdated, int color){
        super(0);
        this.copyP = copyP;
        this.moveP = moveP;
        this.grid = grid;
        this.gridH = grid.length;
        this.gridW = grid[0].length;
        this.health = health;
        this.random = new Random();
        this.hasUpdated = hasUpdated;
        this.color = color;
    }

    public void setLocation(int y, int x){
        this.y = y;
        this.x = x;
    }

    public void update(){
        if(!this.hasUpdated) {
            this.health -= (1 / (1 - this.copyP) + 1 / (1 - this.moveP)) * energyPerAction;
            if(this.health <= 0.0){
                die();
                return;
            }
            if (this.random.nextFloat() < this.moveP) {
                move();
            }
            if (this.random.nextFloat() < this.copyP) {
                copy();
            }
            if (this.energy != 0) {
                this.health += this.energy;
            }
            this.hasUpdated = true;
        }
    }

    public void refresh(){
        this.hasUpdated = false;
    }

    protected void die(){
        this.grid[this.y][this.x] = new Cell(this.energy);
    }

    protected void move(){

        int direction = chooseRandomDirection();

        if(direction >= 0) {
            int newX = this.x;
            int newY = this.y;

            //Determines new coordinates.
            if (direction == 0) {
                newX -= 1;
            } else if (direction == 1) {
                newX += 1;
            } else if (direction == 2) {
                newY -= 1;
            } else if (direction == 3) {
                newY += 1;
            }

            //Determines energy of new location.
            float newEnergy = this.grid[newY][newX].getEnergy();

            //Puts a reference to this cell at the new location in the grid.
            this.grid[newY][newX] = this;

            //Replaces reference to this cell at old location.
            this.grid[this.y][this.x] = new Cell(this.energy);

            //Updates this cell's energy.
            this.energy = newEnergy;

            //Updates this cell's location.
            this.x = newX;
            this.y = newY;
        }
    }

    protected void copy(){

        int direction = chooseRandomDirection();

        if(direction >= 0) {

            int newX = this.x;
            int newY = this.y;

            //Determines new coordinates.
            if (direction == 0) {
                newX -= 1;
            } else if (direction == 1) {
                newX += 1;
            } else if (direction == 2) {
                newY -= 1;
            } else if (direction == 3) {
                newY += 1;
            }


            //Determines energy of new location.
            float newEnergy = this.grid[newY][newX].getEnergy();

            LivingCell newCell;

            boolean mutateMove = false;
            boolean mutateCopy = false;

            if(random.nextFloat() < mutationRate){
                mutateMove = true;
            }
            if(random.nextFloat() < mutationRate){
                mutateCopy = true;
            }

            //Creates a copy of this cell.
            newCell = new LivingCell(mutateCopy ? random.nextFloat() : this.copyP,
                    mutateMove ? random.nextFloat() : this.moveP, this.grid,
                    (float) (this.health / 2.0), true, 
                    mutateCopy || mutateMove ? getRandomColor() : this.color);


            //Puts the new cell at the new location.
            this.grid[newY][newX] = newCell;
            newCell.setLocation(newY, newX);


            //Updates the new cell's energy.
            newCell.energy = newEnergy;

            //Updates this cell's health to one half the original value.
            this.health = (float) (this.health / 2.0);

        }
    }

    public int getRandomColor(){
        int n = random.nextInt(765);
        int color;
        if(n < 255){
            color = Color.rgb(255 - n, n, 0);
        }else if(n < 510){
            color = Color.rgb(0, 510 - n, n - 255);
        }else{
            color = Color.rgb(n - 510, 0, 765 - n);
        }
        return color;
    }

    public int chooseRandomDirection(){
        ArrayList<Integer> possibleDirections = new ArrayList<Integer>();

        if(this.x > 0 && ! (this.grid[this.y][this.x-1] instanceof LivingCell)){
            possibleDirections.add(0); //0 represents west.
        }
        if(this.x < this.gridW - 1 && ! (this.grid[this.y][this.x+1] instanceof LivingCell)){
            possibleDirections.add(1); //1 represents east.
        }
        if(this.y > 0 && ! (this.grid[this.y-1][this.x] instanceof LivingCell)){
            possibleDirections.add(2); //2 represents north.
        }
        if(this.y < this.gridH - 1 && ! (this.grid[this.y+1][this.x] instanceof LivingCell)) {
            possibleDirections.add(3); //3 represents south.
        }

        if(possibleDirections.size() == 0){
            return -1;
        }
        return possibleDirections.get(random.nextInt(possibleDirections.size()));
    }

    @Override
    public int color(){
        return this.color;
    }
}
