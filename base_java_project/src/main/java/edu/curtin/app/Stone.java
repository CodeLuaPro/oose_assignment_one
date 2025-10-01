package edu.curtin.app;

import java.util.List;


//Stone heritage (zoning rule)
public class Stone extends Heritage {

    public Stone(Square inNext) {
        super(inNext);
        
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        if (canBuild && structureType == StructureTypes.STONE) {
            retVal = true;
        } 
        if (structureType != StructureTypes.STONE && reasonsList != null) {
            reasonsList.add("- Structure must be stone");
        }
        return next.checkBuild(structureType, foundationType, numFloors, retVal, reasonsList);
    }
    
}
