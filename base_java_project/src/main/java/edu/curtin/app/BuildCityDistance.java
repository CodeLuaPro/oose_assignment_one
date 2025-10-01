package edu.curtin.app;

//data for the city is chosen based on the distance from the center of the grid
public class BuildCityDistance implements BuildCityOption {

    @Override
    public BuildReport buildCity(Square[][] grid, StructureTypes structureType, FoundationTypes foundationType, int numFloors) throws Exception { //NOPMD
        int width = grid[0].length;
        int height = grid.length;

        StructureTypes chosenStructureType;
        //build report contains the data that must be displayed (number of structures, grid, total cost)
        BuildReport report = new BuildReport(grid.length, grid[0].length);

        //for each square
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //calculate the distance from the center
                int distance = (int)Math.sqrt(Math.pow((i - (height - 1)/2), 2) + Math.pow((j-(width-1)/2), 2));
                //get the number of floors required for that building
                int nFloors = Math.round(1+(20/(distance + 1)));
                //choose the right construction material
                if (distance <= 2) {
                    chosenStructureType = StructureTypes.CONCRETE;
                } else if (distance > 2 && distance <= 4) {
                    chosenStructureType = StructureTypes.BRICK;
                } else if (distance > 4 && distance <= 6) {
                    chosenStructureType = StructureTypes.STONE;
                } else {
                    chosenStructureType = StructureTypes.WOOD;
                }

                //if able to build the structure:
                if (buildStructure(grid, i, j, chosenStructureType, FoundationTypes.SLAB, nFloors)) {
                    //calculate cost, num of buildings and populate the grid of the report
                    double cost = grid[i][j].calcCost(chosenStructureType, nFloors);
                    report.increaseTotalCost(cost);
                    report.incNumStructures();
                    report.setCellFilled(i, j);
                }
                else {
                    report.setCellEmpty(i, j);
                }
            }
        }
        return report;
    }

    @Override
    public boolean buildStructure(Square[][] grid, int row, int col, StructureTypes structureType, FoundationTypes foundationType, int numFloors) {

        return grid[row][col].checkBuild(structureType, foundationType, numFloors, true, null);

    }

    @Override
    public boolean needsExtraInput() {
        return false;
    }
    
}
