package edu.curtin.app;
import java.util.List;


//heritage parent class
public class Heritage extends ZoningRule {

    public Heritage(Square inNext) {
        super(inNext);
        
    }

    @Override
    public double calcCost(StructureTypes structureType, int numFloors) {
        
        return next.calcCost(structureType, numFloors);
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canBuild'");
    }
    
}
