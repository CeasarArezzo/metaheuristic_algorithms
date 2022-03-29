package solver.test;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.ProblemInstance;
import parser.EdgeWeightFormatE;
import parser.EdgeWeightTypeE;
import parser.ProblemTypeE;
import solution.ProblemSolution;
import solver.ClosestNeighSolver;
import solver.ProblemSolver;

public class ClosestNeighSolverTest
{
    
    @Test
    public void testSolveInstance()
    {
        int[][] graph = { {0, 2, 3}, {2, 0, 10}, {4, 5, 0} };
        ProblemInstance pI = new ProblemInstance(graph, "", ProblemTypeE.ATSP, EdgeWeightTypeE.NONE, EdgeWeightFormatE.NONE, 3);
        
        int[] solutions = {16, 10, 16};
        
        for (int i = 0; i < solutions.length; i++)
        {
            ProblemSolver solver = new ClosestNeighSolver(i);
            ProblemSolution solution = solver.solveInstance(pI);
            System.out.println(solution.getPath());
            assertEquals(solutions[i], solution.getObjectiveValue());
        }
        
    }
    
}
