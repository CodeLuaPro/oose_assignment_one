package edu.curtin.app;
import java.util.List;

//Purpose: flood risk zoning rule
public class FloodRisk extends ZoningRule {

    private double riskPercentage;

    public FloodRisk(Square inNext, double inRiskPercentage) {
        super(inNext);
        riskPercentage = inRiskPercentage;
    }

    @Override
    public double calcCost(StructureTypes structureType, int numFloors){
        return next.calcCost(structureType, numFloors) * (1+(riskPercentage/50));
    }

    @Override
    public boolean checkBuild(StructureTypes structureType, FoundationTypes foundationType, int numFloors, boolean canBuild, List<String> reasonsList) {
        boolean retVal = false;
        if (canBuild && numFloors >= 2) {
            retVal = true;
        } else if (numFloors < 2 && reasonsList != null) {
            reasonsList.add("- Must have at least 2 floors");
        }
        return next.checkBuild(structureType, foundationType, numFloors, retVal, reasonsList);
    }
    
}
