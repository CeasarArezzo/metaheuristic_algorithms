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
        ProblemInstance pI = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + "/data/atsp/" + "ftv38.atsp");
        ProblemSolver opt = new Opt2Solver();
        ProblemSolver solver = new TabuSolver(30, 1000, true, true, false);
        ProblemSolution solution = solver.solveInstance(pI);
        ProblemSolution optSolution = opt.solveInstance(pI);
        System.out.println(solution.getObjectiveValue());
        System.out.println(optSolution.getObjectiveValue());


    }
}
