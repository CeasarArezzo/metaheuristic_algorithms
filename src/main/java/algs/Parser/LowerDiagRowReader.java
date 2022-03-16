package algs.Parser;

import algs.Parser.DataReader;

import java.util.Scanner;

public class LowerDiagRowReader implements DataReader
{

    @Override
    public int[][] readInstance(Scanner scanner, int dimension, String currLine)
    {
        return readLowerDiagRowTsp(scanner, dimension, currLine);
    }

    int[][] readLowerDiagRowTsp(Scanner scanner, int dimension, String currLine)
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
}
