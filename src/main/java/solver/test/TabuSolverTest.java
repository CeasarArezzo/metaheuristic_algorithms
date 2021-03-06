package solver.test;

import algs.ProblemInstance;
import org.junit.Test;
import parser.*;
import solution.ProblemSolution;
import solver.Opt2Solver;
import solver.ProblemSolver;
import solver.TabuSolver;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class TabuSolverTest {

    @Test
    public void testSolveInstance() throws FileNotFoundException, WrongNumberException
    {
        ProblemInstance pI = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + "/data/tsp/" + "a280.tsp");
        ProblemSolver opt = new Opt2Solver();
        ProblemSolver solver0 = new TabuSolver(30, 600, false, false, false);
        ProblemSolver solver1 = new TabuSolver(30, 600, false, false, true);
        ProblemSolver solver2 = new TabuSolver(30, 600, false, true, false);
        ProblemSolution solution0 = solver0.solveInstance(pI);
        System.out.println(solution0.getObjectiveValue());
        ProblemSolution solution2 = solver2.solveInstance(pI);
        System.out.println(solution2.getObjectiveValue());
        ProblemSolution solution1 = solver1.solveInstance(pI);
        System.out.println(solution1.getObjectiveValue());
        ProblemSolution optSolution = opt.solveInstance(pI);
        System.out.println(optSolution.getObjectiveValue());
        System.out.println("Expected: 2579");


    }
}
