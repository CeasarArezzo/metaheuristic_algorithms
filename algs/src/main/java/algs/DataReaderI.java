package main.java.algs;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Scanner;


public interface DataReaderI {
    
    enum PROBLEM_TYPE
    {
        TSP, ATSP
    }

    enum EDGE_WEIGHT_FORMAT_TSP
    {
        FULL_MATRIX, LOWER_DIAG_ROW, EMPTY
    }

    enum EDGE_WEIGHT_TYPE_TSP
    {
        EXPLICIT, EUC_2D
    }

    static ProblemInstance readFileForGraphMatrix(String path) throws Exception
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
        String edge_weight_format = "EMPTY";

        if(edge_weight_type.equals("EXPLICIT"))
        {
            edge_weight_format = dictionary.get("EDGE_WEIGHT_FORMAT");
        }

        int[][] dataMatrix;
        PROBLEM_TYPE problemType = PROBLEM_TYPE.valueOf(type);
        EDGE_WEIGHT_FORMAT_TSP formatType = EDGE_WEIGHT_FORMAT_TSP.valueOf(edge_weight_format);
        EDGE_WEIGHT_TYPE_TSP typeType = EDGE_WEIGHT_TYPE_TSP.valueOf(edge_weight_type);

        dataMatrix = switch (problemType)
                {
                    case TSP -> readTspProblem(scanner,  dimension, formatType, typeType, nextLine);
                    case ATSP -> readAtspProblem(scanner,  dimension);
                };


        return new ProblemInstance(dataMatrix, name, type, edge_weight_type, edge_weight_format, dimension); //DUMMY
    }

    static int[][] readAtspProblem(Scanner scanner, int dimension)
    {
        return null; //DUMMY
    }

    static int[][] readTspProblem(Scanner scanner, int dimension, EDGE_WEIGHT_FORMAT_TSP edge_weight_format, EDGE_WEIGHT_TYPE_TSP edge_weight_type, String lastLine) throws Exception
    {
        int[][] dataMatrix;

        dataMatrix = switch (edge_weight_type)
        {

            case EXPLICIT ->  switch (edge_weight_format) {
                case FULL_MATRIX -> readFullMatrixTsp(scanner, dimension, lastLine);
                case LOWER_DIAG_ROW -> readLowerDiagRowTsp(scanner, dimension, lastLine);
                default -> throw new IllegalStateException("Unexpected value: " + edge_weight_format);
            };
            case EUC_2D -> readEuc2DTsp(scanner, dimension, lastLine);
        };

        return dataMatrix; //DUMMY
    }

    static int[][] readEuc2DTsp(Scanner scanner, int dimension, String currLine) throws Exception
    {
        while(!currLine.equals("NODE_COORD_SECTION"))
        {
            currLine = scanner.nextLine();
        }
        double[][] cords = new double[dimension][2];
        int iterator = 0;
        while(scanner.hasNextDouble())
        {
            currLine = scanner.nextLine().replaceAll("\\s+", " ").trim();

            String[] data = currLine.split(" ");
            double xCord = Double.parseDouble(data[1]);
            double yCord = Double.parseDouble(data[2]);

            if (xCord != Math.ceil(xCord) || yCord != Math.ceil(yCord))
            {
                throw new Exception("REAL NUMBER");
            }

            cords[iterator][0] = xCord;
            cords[iterator][1] = yCord;
            iterator++;
        }

        int[][] dataMatrix = new int[dimension][dimension];

        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j < i; j++)
            {
                double xd = cords[i][0] - cords[j][0];
                double yd = cords[i][1] - cords[j][1];
                int dist = (int) (Math.sqrt(xd * xd + yd * yd) + 0.5);
                dataMatrix[i][j] =  dist;
                dataMatrix[j][i] =  dist;
            }
            dataMatrix[i][i] = 0;
        }

        return dataMatrix;
    }

    static int[][] readLowerDiagRowTsp(Scanner scanner, int dimension, String currLine)
    {
        while(!currLine.equals("EDGE_WEIGHT_SECTION"))
        {
            currLine = scanner.nextLine();
        }

        int[][] dataMatrix = new int[dimension][dimension];

        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j <= i; j++)
            {
                dataMatrix[i][j] = scanner.nextInt();
                dataMatrix[j][i] = dataMatrix[i][j];
            }
        }
        return dataMatrix;
    }

    static int[][] readFullMatrixTsp(Scanner scanner, int dimension, String currLine)
    {
        while(!currLine.equals("EDGE_WEIGHT_SECTION"))
        {
            currLine = scanner.nextLine();
        }

        int[][] dataMatrix = new int[dimension][dimension];

        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j < dimension; j++)
            {
                dataMatrix[i][j] = scanner.nextInt();
            }
        }
        return dataMatrix;
    }
}
