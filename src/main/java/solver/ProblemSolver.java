package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

public interface ProblemSolver
{
    ProblemSolution solveInstance(ProblemInstance problemInstance);

    default public ArrayList<Integer> generateRandomPath(int dimension)
    {
        ArrayList<Integer> range = new ArrayList<>(IntStream.rangeClosed(0, dimension - 1)
                .boxed().toList());

        Collections.shuffle(range);

        return new ArrayList<>(range);
    }
}
