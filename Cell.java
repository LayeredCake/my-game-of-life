package com.example.mygameoflife;

import android.graphics.Color;

/**
 * Represents a single cell in a grid.
 */
public class Cell {

    /**
     * The amount of energy contained in this Cell.
     */
    protected float energy;

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
    public Cell(float energy){
        this.energy = energy;
    }

    /**
     * Creates a new Cell with an energy of 0.
     */
    public Cell(){
        this(0);
    }

    /**
     * Getter for energy attribute.
     * @return The energy of this Cell.
     */
    public float getEnergy(){
        return this.energy;
    }

    /**
     * Setter for energy attribute.
     * @param energy The new energy of the Cell.
     */
    public void setEnergy(float energy){
        this.energy = energy;
    }

    /**
     * Determines this Cell's color, represented as an integer.
     * Cells with more energy are brighter.
     * @return The integer form of the Cell's color.
     */
    public int color(){
        int rgb = (int) (this.energy * 255 / scale);
        if(rgb > 255){rgb = 255;}
        if(rgb < 0){rgb = 0;}
        return Color.rgb(rgb, rgb, rgb);
    }
}
