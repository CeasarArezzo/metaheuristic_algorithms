package generator;

import java.util.Random;

import algs.ProblemInstance;

public class BasicATSPProblemGenerator implements ProblemGenerator
{
    
    @Override
    public ProblemInstance generateProblemInstance(int size)
    {
        return generateATSPProblemInstance(size);
    }
    
    public ProblemInstance generateATSPProblemInstance(int size)
    {
        return generateATSPProblemInstance(size, 6 * size);
    }
    
    public ProblemInstance generateATSPProblemInstance(int size, int maxValue)
    {
        int[][] graphMatrix = new int[size][size];
        Random random = new Random();
        
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                graphMatrix[i][j] = random.nextInt(size);
            }
            graphMatrix[i][i] = 0;
        }
        
        String name = "RandomATSP(" + size + ")";
        return new ProblemInstance(graphMatrix, name, "RandomATSP", null, null, size);
    }
    
}
