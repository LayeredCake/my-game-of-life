package com.example.mygameoflife;

import android.graphics.Color;

/**
 * Represents a single cell in a grid.
 */
public class Cell {

    /**
     * The amount of energy contained in this Cell.
     */
    protected double energy;

    /**
     * A scaling factor.
     * Gives relationship between the energy and the color of Cells.
     * Increase scale to make Cells darker.
     */
    public static int scale = 5;

    /**
     * Creates a new Cell with a given energy.
     * @param energy The Cell's energy.
     */
    public Cell(double energy){
        this.energy = energy;
    }

    /**
     * Creates a new Cell with an energy of 0.
     */
    public Cell(){
        this(0);
    }
}
