package parser;

import algs.ProblemInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public interface DataReader 
{

    int[][] readInstance(Scanner scanner, int dimension, String currLine) throws WrongNumberException;

    static ProblemInstance readFileForGraphMatrix(String path) throws FileNotFoundException, WrongNumberException
    {
        File dataFile = new File(path);
        Scanner scanner = new Scanner(dataFile);
        HashMap<String, String> dictionary = new HashMap<>();
        String nextLine = scanner.nextLine();
        while (!nextLine.contains("SECTION"))
        {
            nextLine = nextLine.replace(" ", "");
            String[] keyVal = nextLine.split(":");
            dictionary.put(keyVal[0], keyVal[1]);
            nextLine = scanner.nextLine();
        }

        String name = dictionary.get("NAME");
        String type = dictionary.get("TYPE");
        int dimension = Integer.parseInt(dictionary.get("DIMENSION"));
        String edge_weight_type = dictionary.get("EDGE_WEIGHT_TYPE");
        String edge_weight_format = "NONE";

        if(edge_weight_type.equals("EXPLICIT"))
        {
            edge_weight_format = dictionary.get("EDGE_WEIGHT_FORMAT");
        }
        ProblemTypeE problemType = ProblemTypeE.valueOf(type);
        EdgeWeightFormatE formatType = EdgeWeightFormatE.valueOf(edge_weight_format);
        EdgeWeightTypeE typeType = EdgeWeightTypeE.valueOf(edge_weight_type);

        DataReader dataReader = switch (problemType)
                {
                    case TSP -> readTspProblem(formatType, typeType);
                    case ATSP -> readAtspProblem();
                };


        return new ProblemInstance(dataReader.readInstance(scanner, dimension, nextLine), name, problemType, typeType, formatType, dimension); //DUMMY
    }

    static DataReader readAtspProblem()
    {
        return new FullMatrixReader(); //DUMMY
    }

    static DataReader readTspProblem(EdgeWeightFormatE edge_weight_format, EdgeWeightTypeE edge_weight_type)
    {

        return switch (edge_weight_type)
        {

            case EXPLICIT ->  switch (edge_weight_format) {
                case FULL_MATRIX -> new FullMatrixReader();
                case LOWER_DIAG_ROW -> new LowerDiagRowReader();
                default -> throw new IllegalStateException("Unexpected value: " + edge_weight_format);
            };
            case EUC_2D -> new Euc2dReader();
            default -> throw new IllegalStateException("Unexpected value: " + edge_weight_type);
        };

    }






}
