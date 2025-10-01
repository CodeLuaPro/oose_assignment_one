package edu.curtin.app;
import java.util.List;


//swampy terrain type (base class)
public class Swampy implements Square {
    private double costPerFloor = 20000;
    
    @Override
    public double calcCost(StructureTypes structureType, int numFloors) {
        double multiplier = 0;
        switch (structureType.ordinal()) {
            case 0: //wood
                multiplier = 10000;
                break;
            case 1: //stone
                multiplier = 50000;
                break;
            case 2: //brick
                multiplier = 30000;
                break;
            case 3: //concrete
                multiplier = 20000;
                break;
            default:
                break;
        }
        return numFloors * (costPerFloor + multiplier);
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        if (canBuild && foundationType != FoundationTypes.SLAB && structureType != StructureTypes.WOOD) {
            retVal = true;
        }
        else if (reasonsList != null) {
            if (foundationType == FoundationTypes.SLAB) {
                reasonsList.add("- May not have a slab foundation");
            }
            if (structureType == StructureTypes.WOOD) {
                reasonsList.add("- May not have a wooden structure");
            }
        }
        return retVal;
    }
    
    
}
