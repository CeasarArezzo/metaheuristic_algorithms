package generator;

import java.util.Random;

import algs.ProblemInstance;
import parser.EdgeWeightFormatE;
import parser.EdgeWeightTypeE;
import parser.ProblemTypeE;


public class BasicTSPProblemGenerator implements ProblemGenerator
{
    
    @Override
    public ProblemInstance generateProblemInstance(int size)
    {
        return generateTSPProblemInstance(size);
    }
    
    public ProblemInstance generateTSPProblemInstance(int size)
    {
        return generateTSPProblemInstance(size, 6 * size);
    }
    
    public ProblemInstance generateTSPProblemInstance(int size, int maxValue)
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
        return new ProblemInstance(graphMatrix, name, ProblemTypeE.TSP, EdgeWeightTypeE.NONE, EdgeWeightFormatE.NONE, size);
    }
    
}
