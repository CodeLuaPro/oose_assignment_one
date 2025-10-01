package edu.curtin.app;


//Option to build a city uniformly
public class BuildCityUniform implements BuildCityOption {

    @Override
    public boolean buildStructure(Square[][] grid, int row, int col, StructureTypes structureType, FoundationTypes foundationType, int numFloors) {
        return grid[row][col].checkBuild(structureType, foundationType, numFloors, true, null);
    }

    @Override
    public BuildReport buildCity(Square[][] grid, StructureTypes structureType, FoundationTypes foundationType, int numFloors) throws Exception {
        BuildReport report = new BuildReport(grid.length, grid[0].length);

        //for each square
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //if able to build the structre
                if (buildStructure(grid, i, j, structureType, foundationType, numFloors)) {
                    //calculate required data and add it to the report
                    double cost = grid[i][j].calcCost(structureType, numFloors);

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
    public boolean needsExtraInput() {
        return true;
    }
    
}
