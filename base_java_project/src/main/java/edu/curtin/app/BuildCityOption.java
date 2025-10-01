package edu.curtin.app;


//Purpose: interface for the various options to build a city (strategy pattern)
public interface BuildCityOption {
    public BuildReport buildCity(Square[][] grid, StructureTypes structureType, FoundationTypes foundationType, int numFloors) throws Exception; //NOPMD
    public boolean buildStructure(Square[][] grid, int row, int col, StructureTypes structureType, FoundationTypes foundationType, int numFloors);
    //uniform build option requires input (ie number of floors, material etc)
    public boolean needsExtraInput();
}
