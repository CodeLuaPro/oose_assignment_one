package edu.curtin.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;


public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception { //NOPMD
        if (args.length != 1) {
            throw new IllegalArgumentException("Must supply the filename"); //NOPMD
        }

        boolean quit = false;

        Scanner sc = new Scanner(System.in); //NOPMD
        
        //initialise the map to hold the build city options (strategy pattern)
        Map<Integer, BuildCityOption> buildCityOptions = new HashMap<>();
        int buildUniformNum = 1;
        int buildRandomNum = 2;
        int buildDistanceNum = 3;

        buildCityOptions.put(buildUniformNum, new BuildCityUniform());
        buildCityOptions.put(buildRandomNum, new BuildCityRandom());
        buildCityOptions.put(buildDistanceNum, new BuildCityDistance());

        BuildCityOption curOption = new BuildCityRandom();

        Square[][] grid;      

        //try to open the file with the provided filename
        try (var reader = new BufferedReader(new FileReader(args[0]))) { //NOPMD

            //get first line, as its the one with the grid size
            String line = reader.readLine();
            String[] dimensions = line.split(",");
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            grid = new Square[height][width];  
            populateGrid(args[0], grid, sc);

            //start of the menu
            while (!quit) {
                System.out.println("1. Build structure\n2. Build city\n3. Configure\n4. Quit");
                int menuInput = readInt(sc, 1, 4, "Choose option: ");

                int row;
                int col;
                int numFloors = 0;
                int structureTypeInt;
                int foundationTypeint;
                StructureTypes structureType = null;
                FoundationTypes foundationType = null;

                switch (menuInput) {
                    //case 1 = build a single structure
                    case 1:
                        //ask the user for the row, column, number of floors and other structure information
                        row = readInt(sc, 1, grid.length, "Enter row number: ") - 1;
                        col = readInt(sc, 1, grid[0].length, "Enter column number: ") - 1;
                        numFloors = readInt(sc, 0, Integer.MAX_VALUE, "Enter the number of floors: ");
                        structureTypeInt = readInt(sc, 1, 4, "Enter construction material (1 = wood, 2 = stone, 3 = brick, 4 = concrete): ")-1;
                        foundationTypeint = readInt(sc, 1, 2, "Enter the foundation type (1 = slab, 2 = stilts): ")-1;
                        structureType = StructureTypes.values()[structureTypeInt];
                        foundationType = FoundationTypes.values()[foundationTypeint];
                        List<String> reasonsList = new ArrayList<>();

                        //if able to build structure:
                        if (grid[row][col].checkBuild(structureType, foundationType, numFloors, true, reasonsList)) {
                            double cost = grid[row][col].calcCost(structureType, numFloors);
                            System.out.println("Cost: $" + roundToTwoPlaces(cost));
                        }
                        //if unable to build structure:
                        else {
                            System.out.println("Cannot build, reasons: ");
                            for (String reason : reasonsList) {
                                System.out.println(reason);
                            }
                            reasonsList.clear();
                        }

                        break;
                    //case 2 = build city
                    case 2: 
                        //if building uniformly, must take structure information as input. Otherwise, do not prompt the input
                        if (curOption.needsExtraInput()) {
                            numFloors = readInt(sc, 0, Integer.MAX_VALUE, "Enter the number of floors: ");
                            structureTypeInt = readInt(sc, 1, 4, "Enter construction material (1 = wood, 2 = stone, 3 = brick, 4 = concrete): ")-1;
                            foundationTypeint = readInt(sc, 1, 2, "Enter the foundation type (1 = slab, 2 = stilts): ")-1;
                            structureType = StructureTypes.values()[structureTypeInt];
                            foundationType = FoundationTypes.values()[foundationTypeint];
                        }
                        //build report contains data about the report, such as the number of structures, cost,
                        //and the grid of structures that can be built
                        BuildReport report = curOption.buildCity(grid, structureType, foundationType, numFloors);
                        double totalCost = roundToTwoPlaces(report.getTotalCost());
                        System.out.println("Cost: $" + totalCost);
                        System.out.println("Total structures: " + report.getNumStructures());
                        report.printGrid();
                        break;
                    //case 3 = choose build city option
                    case 3:
                        System.out.println("Choose a \"build city\" option: (1 = Uniform, 2 = Random, 3 = Central)");
                        int buildOptionInt = readInt(sc, 1, buildCityOptions.size(), "Choose option: ");
                        //get the right option from the map
                        curOption = buildCityOptions.get(buildOptionInt);
                        break;
                    case 4:
                        quit = true;
                        break;
                    default:
                        break;
                }
            }
            
        } catch (IOException e) {
            logger.severe("Could not open the file");
        } catch (NumberFormatException | FileParsingException e2) {
            logger.severe("File must be valid");
        }
        finally {
            sc.close();
        }
    }

    //Purpose: parse the file, create squares and populate the grid with the squares
    public static void populateGrid(String filename, Square[][] grid, Scanner sc) throws Exception { //NOPMD
        Square square = null;
        int curRow = 0;
        int curCol = 0;
        int numRecords = 0;
        try (var reader = new BufferedReader(new FileReader(filename))) {
            //discard first line because it contains the size
            reader.readLine();
            String line;
            //while there are still lines...
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    //create a square of the base class (decorator pattern)
                    switch (parts[0]) {
                        case "flat":
                            square = new Flat();
                            break;
                        case "swampy":
                            square = new Swampy();
                            break;
                        case "rocky":
                            square = new Rocky();
                            break;
                        default:
                            
                            throw new FileParsingException("line must contain the square type!"); 
                    }
                    //take the rest of the line and apply the zoning rules
                    square = addZoneRules(square, parts, sc);
                    grid[curRow][curCol] = square;
                    
                    //shift a column to the right, or drop to the next row and start from the first column
                    if (curCol == grid[0].length - 1) {
                        curCol = 0;
                        curRow++;
                    } else {
                        curCol++;
                    }
                    numRecords++;
                } 
            }
        }
        catch (IOException e) {
            logger.severe("Could not open the file");
        }
        catch (NumberFormatException e2) {
            logger.severe("File must be valid");
        }

        if (curCol == 0 && curRow == 0) {
            logger.warning("The file only contains the grid size");
            
            throw new FileParsingException("Empty grid"); 
        }
        if (numRecords != grid.length * grid[0].length) {
            logger.warning("File contains an invalid number of records");
            
            throw new FileParsingException("File contains an invalid number of records"); 
        }
     }

    //Purpose: returns a square wrapped in zoning rules (decorator pattern)
    public static Square addZoneRules(Square square, String[] parts, Scanner sc) throws Exception { //NOPMD
        //for each zoning rule in the line...
        for (int i = 1; i < parts.length; i++) {
            String currentZoneRule = parts[i];

            //found contamination
            if (currentZoneRule.contains("contamination")) {
                if (!currentZoneRule.equals("contamination")) {
                    logger.warning("Invalid contamination rule in file");
                    
                    throw new FileParsingException("Invalid contamination rule in file"); 
                }
                square = new Contamination(square);
            }
            //found height limit
            else if (currentZoneRule.contains("height-limit")) {
                String[] heightLimitParts = currentZoneRule.split("=");
                double numFloors = Double.parseDouble(heightLimitParts[1]);
                if (numFloors == 0.0 || numFloors - (int)numFloors != 0) {
                    logger.severe("invalid height limit in file");
                    
                    throw new FileParsingException("invalid height limit in file"); 
                }
                square = new HeightLimit(square, (int)numFloors);
                
            }
            //found heritage rule
            else if (currentZoneRule.contains("heritage")) {
                String[] heritageParts = currentZoneRule.split("=");
                String heritage = heritageParts[1];
                switch (heritage) {
                    case "wood":
                        square = new Wood(square);
                        break;
                    case "stone":
                        square = new Stone(square);
                        break;
                    case "brick":
                        square = new Brick(square);
                        break;
                    default:
                        logger.severe("Invalid heritage in file");
                        
                        throw new FileParsingException("Invalid heritage in file"); 
                        
                }
            }
            //found flood risk
            else if (currentZoneRule.contains("flood-risk")) {
                String[] floodRiskParts = currentZoneRule.split("=");
                double floodRisk = Double.parseDouble(floodRiskParts[1]);
                if (floodRisk < 0 || floodRisk > 100) {
                    logger.severe("Flood risk is invalid");
                    
                    throw new FileParsingException("Flood risk is invalid"); 
                }
                square = new FloodRisk(square, floodRisk);
            }
            //none of those were present, invalid zoning rule found
            else {
                logger.severe("Invalid zoning rules");
                
                throw new FileParsingException("Invalid zoning rules"); 
            }
        }
        return square;
    }

    public static int readInt(Scanner sc, int min, int max, String prompt) {
        System.out.print(prompt);
        boolean valid = false;
        int input = -1;
        while (!valid) {
            valid = true;
            try {
                input = sc.nextInt();
                if (input < min || input > max) {
                    valid = false;
                    System.out.print("Enter a valid integer: ");
                    sc.nextLine();
                }
            }  catch (InputMismatchException e) {
                System.out.print("Enter a valid integer: ");
                sc.nextLine();
                valid = false;
            }
            
        }
        return input;
    }

    public static double roundToTwoPlaces(double val) {
        return Math.round(val*100.0)/100.0;
    }
    
}