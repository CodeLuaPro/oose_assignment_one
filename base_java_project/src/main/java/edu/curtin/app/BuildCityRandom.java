package edu.curtin.app;



//randomly choose data for each square to build the city
public class BuildCityRandom implements BuildCityOption {

    @Override
    public BuildReport buildCity(Square[][] grid, StructureTypes structureType, FoundationTypes foundationType, int numFloors) throws Exception {
        //make arrays of the foundation & material enums to randomly choose one of each later
        StructureTypes[] allStructures = StructureTypes.values();
        FoundationTypes[] allFoundations = FoundationTypes.values();

        BuildReport report = new BuildReport(grid.length, grid[0].length);
        //for each square...
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //get random number of floors (assume 100 floors is the limit)
                int randomFloors = (int)(Math.random() * 100);
                StructureTypes randomStructure = allStructures[(int)(Math.random() * allStructures.length)];
                FoundationTypes randomFoundation = allFoundations[(int)(Math.random() * allFoundations.length)];

                //if able to build the structure on that square...
                if (buildStructure(grid, i, j, randomStructure, randomFoundation, randomFloors)) {
                    //calculate the required data and add it to the report
                    double cost = grid[i][j].calcCost(randomStructure, randomFloors);
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
