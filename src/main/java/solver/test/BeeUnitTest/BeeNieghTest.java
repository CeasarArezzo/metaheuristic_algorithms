package solver.test.BeeUnitTest;

import algs.ProblemInstance;
import org.junit.Test;
import parser.DataReader;
import parser.WrongNumberException;
import solution.ProblemSolution;
import solver.Opt2Solver;
import solver.ProblemSolver;
import solver.bees.Bee;
import solver.bees.BeeNeigh;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class BeeNieghTest
{
    @Test
    public void testSolveInstance() throws FileNotFoundException, WrongNumberException
    {
        ProblemInstance pI = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + "/data/tsp/" + "a280.tsp");

        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        ArrayList<Integer> b = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        Bee c = new Bee(new ArrayList<ArrayList<Integer>>(Arrays.asList(a, b)), pI, 10, BeeNeigh.INSERT, null);

        System.out.println(c.insert(0, 3, a));
        System.out.println(c.insert(3, 4, a));
        System.out.println(c.insert(1, 5, a));


    }
}
