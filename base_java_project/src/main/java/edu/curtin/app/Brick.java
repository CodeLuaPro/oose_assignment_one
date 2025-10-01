package edu.curtin.app;

import java.util.List;

//Brick heritage (zoning rule)
public class Brick extends Heritage {

    public Brick(Square inNext) {
        super(inNext);
        
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        if (canBuild && structureType == StructureTypes.BRICK) {
            retVal = true;
        } 
        if (structureType != StructureTypes.BRICK && reasonsList != null) {
            reasonsList.add("- Structure must be brick");
        }
        return next.checkBuild(structureType, foundationType, numFloors, retVal, reasonsList);
    }
}
