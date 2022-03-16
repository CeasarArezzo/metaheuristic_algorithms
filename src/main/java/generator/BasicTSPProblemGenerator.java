package generator;

import java.util.Random;

import algs.ProblemInstance;

public class BasicTSPProblemGenerator implements ProblemGenerator
{
    
    @Override
    public ProblemInstance generateProblemInstance(int size)
    {
        // TODO Auto-generated method stub
        return null;
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
            for (int j = i + 1; j < size; j++)
            {
                graphMatrix[i][j] = random.nextInt(size);
                graphMatrix[j][i] = graphMatrix[i][j];
            }
            graphMatrix[i][i] = 0;
        }
        
        String name = "RandomTSP(" + size + ")";
        return new ProblemInstance(graphMatrix, name, "RandomTSP", null, null, size);
    }
    
}
