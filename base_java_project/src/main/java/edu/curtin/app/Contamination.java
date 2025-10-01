package edu.curtin.app;

import java.util.List;


//Contamination zoning rule
public class Contamination extends ZoningRule {

    public Contamination(Square inNext) {
        super(inNext);
        
    }

    @Override
    public double calcCost(StructureTypes structureType, int numFloors){
        
        return next.calcCost(structureType, numFloors) * 1.5;
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        return next.checkBuild(structureType, foundationType, numFloors, canBuild, reasonsList);
    }
    
}
