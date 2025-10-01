package edu.curtin.app;

//Purpose: contains the data that must be displayed after attempting to build a city

import java.util.logging.Logger;

public class BuildReport {
    private static final Logger logger = Logger.getLogger(BuildReport.class.getName());

    private char[][] buildingGrid;
    private double totalCost = 0;
    private int numStructures = 0;

    public BuildReport(int height, int width) {
        buildingGrid = new char[height][width];
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getNumStructures() {
        return numStructures;
    }

    public void printGrid() {
        for (char[] buildingGrid1 : buildingGrid) {
            for (char j = 0; j < buildingGrid[0].length; j++) {
                System.out.print(buildingGrid1[j]);
                if (j == buildingGrid[0].length-1) {
                    System.out.print("\n");
                }
            }
        }
    }

    public void increaseTotalCost(double addedCost) {
        this.totalCost += addedCost;
    }

    public void incNumStructures() {
        this.numStructures++;
    }

    public void setCellFilled(int row, int col) throws Exception { //NOPMD
        if (row < 0 || row > buildingGrid.length || col < 0 || col > buildingGrid[0].length) {
            logger.severe("The chosen row/column must be within the bounds of the grid");
            throw new IllegalArgumentException("cell must be inside the grid");
        }
        buildingGrid[row][col] = 'X';
    }
    public void setCellEmpty(int row, int col) throws Exception { //NOPMD
        if (row < 0 || row > buildingGrid.length || col < 0 || col > buildingGrid[0].length) {
            logger.severe("The chosen row/column must be within the bounds of the grid");
            throw new IllegalArgumentException("cell must be inside the grid");
        }
        buildingGrid[row][col] = '.';
    }
}
