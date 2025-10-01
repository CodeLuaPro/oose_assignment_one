package edu.curtin.app;
import java.util.List;


//Wood heritage type (zoning rule)
public class Wood extends Heritage {

    public Wood(Square inNext) {
        super(inNext);
        
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        if (canBuild && structureType == StructureTypes.WOOD) {
            retVal = true;
        }
        else if (structureType != StructureTypes.WOOD && reasonsList != null) {
            reasonsList.add("- Must be a wooden structure");
        }
        return next.checkBuild(structureType, foundationType, numFloors, retVal, reasonsList);
    }
    
}
