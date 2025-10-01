package edu.curtin.app;
import java.util.List;


//Flat terrain type (base class)
public class Flat implements Square {
    

    @Override
    public double calcCost(StructureTypes structureType, int numFloors){
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
        return multiplier * numFloors;
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        if (canBuild == true) {
            retVal = true;
        }
        return retVal;
    }
}
