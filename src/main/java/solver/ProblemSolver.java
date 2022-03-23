package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public interface ProblemSolver
{
    ProblemSolution solveInstance(ProblemInstance problemInstance);

    default public ArrayList<Integer> generateRandomPath(int dimension)
    {
        List<Integer> range = IntStream.rangeClosed(0, dimension - 1)
                .boxed().toList();

        java.util.Collections.shuffle(range);

        return new ArrayList<>(range);
    }
}
