package solver.test;

import algs.ProblemInstance;
import org.junit.Test;
import parser.EdgeWeightFormatE;
import parser.EdgeWeightTypeE;
import parser.ProblemTypeE;
import solution.ProblemSolution;
import solver.ClosestNeighSolver;
import solver.Opt2Solver;
import solver.ProblemSolver;

import static org.junit.Assert.assertEquals;

public class Opt2SolverTest
{
    @Test
    public void testSolveInstance()
    {
        int[][] graph = { {0, 2, 3}, {2, 0, 10}, {4, 5, 0} };
        ProblemInstance pI = new ProblemInstance(graph, "", ProblemTypeE.ATSP, EdgeWeightTypeE.NONE, EdgeWeightFormatE.NONE, 3);

        int[] solutions = {10, 10, 10};

        for (int i = 0; i < solutions.length; i++)
        {
            ProblemSolver solver = new Opt2Solver();
            ProblemSolution solution = solver.solveInstance(pI);
            System.out.println(solution.getPath());
            assertEquals(solutions[i], solution.getObjectiveValue());
        }

    }
}
