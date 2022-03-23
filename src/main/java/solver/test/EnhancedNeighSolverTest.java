package solver.test;

import static org.junit.Assert.*;

import org.junit.Test;

import algs.ProblemInstance;
import solution.ProblemSolution;
import solver.ClosestNeighSolver;
import solver.EnhancedNeighSolver;
import solver.ProblemSolver;

public class EnhancedNeighSolverTest
{

    @Test
    public void testSolveInstance()
    {
        int[][] graph = { {0, 2, 3}, {2, 0, 10}, {4, 5, 0} };
        ProblemInstance pI = new ProblemInstance(graph, "", "", "", "", 3);
        
        int[] solutions = {10, 10, 10};
        
        for (int i = 0; i < solutions.length; i++)
        {
            ProblemSolver solver = new EnhancedNeighSolver();
            ProblemSolution solution = solver.solveInstance(pI);
            System.out.println(solution.getPath());
            assertEquals(solutions[i], solution.getObjectiveValue());
        }
    }
}
