package parser;

import java.util.Scanner;

public class Euc2dReader implements DataReader
{

    @Override
    public int[][] readInstance(Scanner scanner, int dimension, String currLine) throws Exception
    {
        return readEuc2DTsp(scanner, dimension, currLine);
    }

    int[][] readEuc2DTsp(Scanner scanner, int dimension, String currLine) throws Exception
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
}
