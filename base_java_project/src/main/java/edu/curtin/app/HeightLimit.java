package edu.curtin.app;
import java.util.List;


//Height limit zoning rule
public class HeightLimit extends ZoningRule {

    private int floorLimit;

    public HeightLimit(Square inNext, int inNumFloors) {
        super(inNext);
        floorLimit = inNumFloors;
        
    }

    @Override
    public double calcCost(StructureTypes structureType, int numFloors){
        return next.calcCost(structureType, numFloors);
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        
        if (canBuild && numFloors <= floorLimit) {
            retVal = true;
        } else if (numFloors > floorLimit && reasonsList != null) {
            reasonsList.add("- Number of floors exceeds the limit");
        }
        return next.checkBuild(structureType, foundationType, numFloors, retVal, reasonsList);
    }
    
}
