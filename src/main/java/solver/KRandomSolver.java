package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KRandomSolver implements ProblemSolver
{
    private final int numOfIterations;

    public KRandomSolver(int numOfIterations)
    {
        this.numOfIterations = numOfIterations;
    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        int dimension = problemInstance.getDimension();

        ArrayList<Integer> bestPath = new ArrayList<>();
        int bestValue = 0;
        for(int i = 0; i < numOfIterations; i++)
        {
            ArrayList<Integer> currPath = generateRandomPath(dimension);
            int currValue = ProblemSolution.getObjectiveValue(currPath.get(0), currPath, problemInstance);
            if(currValue < bestValue)
            {
                bestValue = currValue;
                bestPath = currPath;
            }
        }

        return new ProblemSolution(bestPath.get(0), bestValue, problemInstance);
    }

    public ArrayList<Integer> generateRandomPath(int dimension)
    {
        List<Integer> range = IntStream.rangeClosed(0, dimension - 1)
                .boxed().toList();

        java.util.Collections.shuffle(range);

        return new ArrayList<>(range);
    }
}
