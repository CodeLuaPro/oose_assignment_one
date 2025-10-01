package edu.curtin.app;

import java.util.List;


//Purpose: interface for all squares and zoning rules (decorator pattern)
public interface Square {
    
    public double calcCost(StructureTypes structureType, int numFloors);

    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList);
}
